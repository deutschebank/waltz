import React from "react";
import {render, screen, fireEvent, waitFor} from "@testing-library/react";
import "@testing-library/jest-dom";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import BookmarkPanel from "./BookmarkPanel";
import {useToasts} from "../../context/toast/ToastContext";
import {bookmarkApi} from "../../api/bookmark";
import {userManagementApi} from "../../api/user-management";
import {enumValueApi} from "../../api/enum-value";
import {EntityReference} from "../../types/Entity";
import {BookmarkType} from "../../types/Bookmark";
import roles from "../../../user/system-roles";

// Mock dependencies
jest.mock("../../context/toast/ToastContext", () => ({
  useToasts: jest.fn(),
}));

jest.mock("../../api/bookmark", () => ({
  bookmarkApi: {
    load: jest.fn(),
    save: jest.fn(),
    remove: jest.fn(),
  },
}));

jest.mock("../../api/user-management", () => ({
  userManagementApi: {
    load: jest.fn(),
  },
}));

jest.mock("../../api/enum-value", () => ({
  enumValueApi: {
    load: jest.fn(),
  },
}));

const mockAddToast = jest.fn();

const primaryEntityRef: EntityReference = {
  id: 1,
  kind: "APPLICATION",
  name: "Test App",
  description: "",
  externalId: "",
  entityLifecycleStatus: "UNKNOWN",
};

const mockBookmarks: BookmarkType[] = [
  {
    id: 1,
    bookmarkKind: "DOCUMENTATION",
    title: "Doc 1",
    url: "http://doc1.com",
    parent: primaryEntityRef,
    lastUpdatedBy: "test",
  },
  {
    id: 2,
    bookmarkKind: "EXAMPLE",
    title: "Example 1",
    url: "http://example1.com",
    parent: primaryEntityRef,
    lastUpdatedBy: "test",
  },
];

const mockEnums = [
  {key: "DOCUMENTATION", name: "Documentation", type: "bookmarkKind"},
  {key: "EXAMPLE", name: "Example", type: "bookmarkKind"},
];

const mockUser = {
  userName: "testuser",
  roles: [roles.BOOKMARK_EDITOR.key],
};

const mockUserWithoutPerms = {
  userName: "viewonly",
  roles: [],
};

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false,
    },
  },
});

const renderComponent = (
  bookmarks: BookmarkType[] | null = mockBookmarks,
  user: any = mockUser,
  isLoading = false,
  error: Error | null = null
) => {
  (bookmarkApi.load as jest.Mock).mockReturnValue({
    queryKey: ["bookmarks", "load", primaryEntityRef.kind, primaryEntityRef.id],
    queryFn: () => (error ? Promise.reject(error) : Promise.resolve(bookmarks)),
    isLoading,
    error,
  });
  (userManagementApi.load as jest.Mock).mockReturnValue({
    queryKey: ["user"],
    queryFn: () => Promise.resolve(user),
  });
  (enumValueApi.load as jest.Mock).mockReturnValue({
    queryKey: ["enums"],
    queryFn: () => Promise.resolve(mockEnums),
  });

  return render(
    <QueryClientProvider client={queryClient}>
      <BookmarkPanel primaryEntityRef={primaryEntityRef} />
    </QueryClientProvider>
  );
};

describe("BookmarkPanel", () => {
  beforeEach(() => {
    jest.clearAllMocks();
    (useToasts as jest.Mock).mockReturnValue({addToast: mockAddToast});
    queryClient.clear();
  });

  it("should render loader while bookmarks are loading", () => {
    renderComponent(null, mockUser, true);
    expect(screen.getByTestId("loader")).toBeInTheDocument();
  });

  it("should render error message if loading bookmarks fails", async () => {
    const error = new Error("Failed to load");
    renderComponent(null, mockUser, false, error);
    await waitFor(() => {
      expect(
        screen.getByText(`Error loading bookmarks: ${error.message}`)
      ).toBeInTheDocument();
    });
  });

  it("should render no data message when there are no bookmarks", async () => {
    renderComponent([]);
    await waitFor(() => {
      expect(screen.getByText("No bookmarks")).toBeInTheDocument();
    });
  });

  it("should display bookmarks and categories", async () => {
    renderComponent();
    await waitFor(() => {
      expect(screen.getByText("Doc 1")).toBeInTheDocument();
      expect(screen.getByText("Example 1")).toBeInTheDocument();
    });
  });

  it("should allow adding a bookmark if user has permissions", async () => {
    renderComponent([]);
    await waitFor(() => {
      expect(screen.getByText("No bookmarks")).toBeInTheDocument();
    });

    const addButton = screen.getByRole("button", {name: /Add bookmark/i});
    expect(addButton).toBeInTheDocument();
    fireEvent.click(addButton);

    await waitFor(() => {
      expect(screen.getByText("Save")).toBeInTheDocument();
    });
  });

  it("should not show add/edit/remove actions if user lacks permissions", async () => {
    renderComponent(mockBookmarks, mockUserWithoutPerms);
    await waitFor(() => {
      expect(screen.getByText("Doc 1")).toBeInTheDocument();
    });
    expect(screen.queryByRole("button", {name: /Add bookmark/i})).not.toBeInTheDocument();
    expect(screen.queryByRole("button", {name: "Edit"})).not.toBeInTheDocument();
    expect(screen.queryByRole("button", {name: "Remove"})).not.toBeInTheDocument();
  });

  it("should filter bookmarks by search query", async () => {
    // Need more than 5 bookmarks to show search input
    const manyBookmarks = [
      ...mockBookmarks,
      {
        id: 3,
        bookmarkKind: "DOCUMENTATION",
        title: "Doc 2",
        url: "http://doc2.com",
        parent: primaryEntityRef,
        lastUpdatedBy: "test",
      },
      {
        id: 4,
        bookmarkKind: "DOCUMENTATION",
        title: "Doc 3",
        url: "http://doc3.com",
        parent: primaryEntityRef,
        lastUpdatedBy: "test",
      },
      {
        id: 5,
        bookmarkKind: "DOCUMENTATION",
        title: "Doc 4",
        url: "http://doc4.com",
        parent: primaryEntityRef,
        lastUpdatedBy: "test",
      },
      {
        id: 6,
        bookmarkKind: "DOCUMENTATION",
        title: "Another",
        url: "http://another.com",
        parent: primaryEntityRef,
        lastUpdatedBy: "test",
      },
    ];
    renderComponent(manyBookmarks);

    let searchInput: HTMLElement;
    await waitFor(() => {
      searchInput = screen.getByPlaceholderText("Search bookmarks...");
      expect(searchInput).toBeInTheDocument();
    });

    fireEvent.change(searchInput!, {target: {value: "Doc"}});

    await waitFor(() => {
      expect(screen.getByText("Doc 1")).toBeInTheDocument();
      expect(screen.queryByText("Example 1")).not.toBeInTheDocument();
      expect(screen.queryByText("Another")).not.toBeInTheDocument();
    });
  });

  it("should open the editor when edit is clicked", async () => {
    renderComponent();
    await waitFor(() => {
      expect(screen.getByText("Doc 1")).toBeInTheDocument();
    });

    const editButton = screen.getAllByRole("button", {name: "Edit"})[0];
    fireEvent.click(editButton);

    await waitFor(() => {
      expect(screen.getByText("Save")).toBeInTheDocument();
      expect(screen.getByDisplayValue("Doc 1")).toBeInTheDocument();
    });
  });

  it("should open the removal confirmation when remove is clicked", async () => {
    renderComponent();
    await waitFor(() => {
      expect(screen.getByText("Doc 1")).toBeInTheDocument();
    });

    const removeButton = screen.getAllByRole("button", {name: "Remove"})[0];
    fireEvent.click(removeButton);

    await waitFor(() => {
      expect(
        screen.getByText(/Are you sure you want to remove this bookmark/)
      ).toBeInTheDocument();
    });
  });

  it("should save a new bookmark and show success toast", async () => {
    const mockSaveFn = jest.fn().mockResolvedValue(3);
    (bookmarkApi.save as jest.Mock).mockReturnValue({mutationFn: mockSaveFn});

    renderComponent([]);
    await waitFor(() => {
      expect(screen.getByText("No bookmarks")).toBeInTheDocument();
    });

    fireEvent.click(screen.getByRole("button", {name: /Add bookmark/i}));

    await waitFor(() => {
      expect(screen.getByText("Save")).toBeInTheDocument();
    });

    fireEvent.change(screen.getByLabelText("Title"), {target: {value: "New Bookmark"}});
    fireEvent.change(screen.getByLabelText("URL (required)"), {target: {value: "http://new.com"}});
    fireEvent.click(screen.getByRole("button", {name: "Save"}));

    await waitFor(() => {
      expect(mockSaveFn).toHaveBeenCalled();
      expect(mockAddToast).toHaveBeenCalledWith({
        type: "SUCCESS",
        message: "Bookmark saved successfully",
      });
    });
  });

  it("should handle save error and show error toast", async () => {
    const error = {data: {message: "Save failed"}};
    const mockSaveFn = jest.fn().mockRejectedValue(error);
    (bookmarkApi.save as jest.Mock).mockReturnValue({mutationFn: mockSaveFn});

    renderComponent([]);
    await waitFor(() => {
      fireEvent.click(screen.getByRole("button", {name: /Add bookmark/i}));
    });

    await waitFor(() => {
      fireEvent.click(screen.getByRole("button", {name: "Save"}));
    });

    await waitFor(() => {
      expect(mockSaveFn).toHaveBeenCalled();
      expect(mockAddToast).toHaveBeenCalledWith({
        type: "ERROR",
        message: "Save failed",
      });
    });
  });

  it("should remove a bookmark and show success toast", async () => {
    const mockRemoveFn = jest.fn().mockResolvedValue(1);
    (bookmarkApi.remove as jest.Mock).mockReturnValue({mutationFn: mockRemoveFn});

    renderComponent();
    await waitFor(() => {
      expect(screen.getByText("Doc 1")).toBeInTheDocument();
    });

    fireEvent.click(screen.getAllByRole("button", {name: "Remove"})[0]);

    await waitFor(() => {
      expect(screen.getByText(/Are you sure/)).toBeInTheDocument();
    });

    fireEvent.click(screen.getByRole("button", {name: "Remove"}));

    await waitFor(() => {
      expect(mockRemoveFn).toHaveBeenCalled();
      expect(mockAddToast).toHaveBeenCalledWith({
        type: "SUCCESS",
        message: "Bookmark removed successfully",
      });
    });
  });

  it("should handle remove error and show error toast", async () => {
    const error = {data: {message: "Remove failed"}};
    const mockRemoveFn = jest.fn().mockRejectedValue(error);
    (bookmarkApi.remove as jest.Mock).mockReturnValue({mutationFn: mockRemoveFn});

    renderComponent();
    await waitFor(() => {
      fireEvent.click(screen.getAllByRole("button", {name: "Remove"})[0]);
    });

    await waitFor(() => {
      fireEvent.click(screen.getByRole("button", {name: "Remove"}));
    });

    await waitFor(() => {
      expect(mockRemoveFn).toHaveBeenCalled();
      expect(mockAddToast).toHaveBeenCalledWith({
        type: "ERROR",
        message: "Remove failed",
      });
    });
  });
});
