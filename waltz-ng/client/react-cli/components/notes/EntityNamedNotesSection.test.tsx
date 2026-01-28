import React from "react";
import {render, screen, fireEvent, waitFor} from "@testing-library/react";
import "@testing-library/jest-dom";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import EntityNamedNotesSection from "./EntityNamedNotesSection";
import {noteApi} from "../../api/note";
import {noteTypeApi} from "../../api/note-type";
import {mkRef} from "../../utils/mkRef";
import {useToasts} from "../../context/toast/ToastContext";
import {NotificationTypeEnum} from "../../enums/Notification";

jest.mock("../../api/note");
jest.mock("../../api/note-type");
jest.mock("../../context/toast/ToastContext", () => ({
  useToasts: jest.fn(),
}));

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      retry: false,
    },
  },
});

const mockAddToast = jest.fn();

const mockParentEntityRef = mkRef({id: 1, kind: "APPLICATION" as const});

const mockNoteTypes = [
  {
    entity: {id: 1, name: "General", description: "General Note"},
    operations: ["ADD", "UPDATE", "REMOVE"],
  },
  {
    entity: {id: 2, name: "Audit", description: "Audit Note"},
    operations: ["ADD", "UPDATE"],
  },
];

const mockNotes = [
  {
    note: {
      id: 101,
      namedNoteTypeId: 1,
      noteText: "This is a general note.",
      entityReference: mockParentEntityRef,
    },
    type: mockNoteTypes[0].entity,
    operations: mockNoteTypes[0].operations,
  },
];

const wrapper = ({children}: {children: React.ReactNode}) => (
  <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

describe("EntityNamedNotesSection", () => {
  beforeEach(() => {
    jest.clearAllMocks();
    (noteTypeApi.findForRefAndUser as jest.Mock).mockReturnValue({
      queryKey: ["noteType", "findForRefAndUser", mockParentEntityRef],
      queryFn: () => Promise.resolve(mockNoteTypes),
    });
    (noteApi.findForEntityReference as jest.Mock).mockReturnValue({
      queryKey: ["note", "findForEntityReference", mockParentEntityRef],
      queryFn: () => Promise.resolve(mockNotes.map((n) => n.note)),
    });
    (useToasts as jest.Mock).mockReturnValue({
      addToast: mockAddToast,
    });
  });

  it("should display existing notes", async () => {
    render(<EntityNamedNotesSection parentEntityRef={mockParentEntityRef} />, {wrapper});

    expect(await screen.findByText("General")).toBeInTheDocument();
    expect(screen.getByText("This is a general note.")).toBeInTheDocument();
  });

  it("should show the add panel when 'Add a new note' is clicked", async () => {
    render(<EntityNamedNotesSection parentEntityRef={mockParentEntityRef} />, {wrapper});

    const addButton = await screen.findByText("Add a new note");
    fireEvent.click(addButton);

    expect(screen.getByText("Add a new note:")).toBeInTheDocument();
    expect(screen.getByText("General")).toBeInTheDocument();
    expect(screen.getByText("Audit")).toBeInTheDocument();
  });

  it("should show the update panel when 'Edit' is clicked", async () => {
    render(<EntityNamedNotesSection parentEntityRef={mockParentEntityRef} />, {wrapper});

    const editButton = await screen.findByText("Edit");
    fireEvent.click(editButton);

    expect(screen.getByText("Editing: General")).toBeInTheDocument();
    expect(screen.getByDisplayValue("This is a general note.")).toBeInTheDocument();
  });

  it("should show the removal confirmation when 'Delete' is clicked", async () => {
    render(<EntityNamedNotesSection parentEntityRef={mockParentEntityRef} />, {wrapper});

    const deleteButton = await screen.findByText("Delete");
    fireEvent.click(deleteButton);

    expect(
      screen.getByText("Are you sure you would like to remove this note?")
    ).toBeInTheDocument();
    expect(screen.getByText("This is a general note.")).toBeInTheDocument();
  });

  it("should call the save mutation when creating a new note", async () => {
    const saveMock = jest.fn().mockResolvedValue(true);
    (noteApi.save as jest.Mock).mockReturnValue({mutationFn: saveMock});

    render(<EntityNamedNotesSection parentEntityRef={mockParentEntityRef} />, {wrapper});

    const addButton = await screen.findByText("Add a new note");
    fireEvent.click(addButton);

    fireEvent.click(screen.getByText("Audit"));

    const textarea = screen.getByPlaceholderText(
      "Enter note text here. Markdown formatting is supported."
    );
    fireEvent.change(textarea, {target: {value: "New audit note"}});
    fireEvent.click(screen.getByText("Save"));

    await waitFor(() => {
      expect(saveMock).toHaveBeenCalled();
    });
    expect(mockAddToast).toHaveBeenCalledWith({
      type: NotificationTypeEnum.SUCCESS,
      message: "Saved note",
    });
  });

  it("should call the save mutation when updating a note", async () => {
    const saveMock = jest.fn().mockResolvedValue(true);
    (noteApi.save as jest.Mock).mockReturnValue({mutationFn: saveMock});

    render(<EntityNamedNotesSection parentEntityRef={mockParentEntityRef} />, {wrapper});

    const editButton = await screen.findByText("Edit");
    fireEvent.click(editButton);

    const textarea = screen.getByDisplayValue("This is a general note.");
    fireEvent.change(textarea, {target: {value: "Updated general note"}});
    fireEvent.click(screen.getByText("Save"));

    await waitFor(() => {
      expect(saveMock).toHaveBeenCalled();
    });
    expect(mockAddToast).toHaveBeenCalledWith({
      type: NotificationTypeEnum.SUCCESS,
      message: "Updated note",
    });
  });

  it("should call the remove mutation when a note is deleted", async () => {
    const removeMock = jest.fn().mockResolvedValue(true);
    (noteApi.remove as jest.Mock).mockReturnValue({mutationFn: removeMock});

    render(<EntityNamedNotesSection parentEntityRef={mockParentEntityRef} />, {wrapper});

    const deleteButton = await screen.findByText("Delete");
    fireEvent.click(deleteButton);

    fireEvent.click(screen.getByText("Yes"));

    await waitFor(() => {
      expect(removeMock).toHaveBeenCalled();
    });
    expect(mockAddToast).toHaveBeenCalledWith({
      type: NotificationTypeEnum.SUCCESS,
      message: "Removed note",
    });
  });

  it("should handle cancellation of actions", async () => {
    render(<EntityNamedNotesSection parentEntityRef={mockParentEntityRef} />, {wrapper});

    // Test cancel from add
    const addButton = await screen.findByText("Add a new note");
    fireEvent.click(addButton);
    fireEvent.click(screen.getByText("Cancel"));
    expect(await screen.findByText("Note Type")).toBeInTheDocument();
    expect(mockAddToast).toHaveBeenCalledWith({
      type: "INFO",
      message: "Cancelled addition of note",
    });

    // Test cancel from edit
    const editButton = await screen.findByText("Edit");
    fireEvent.click(editButton);
    fireEvent.click(screen.getByText("Cancel"));
    expect(await screen.findByText("Note Type")).toBeInTheDocument();
    expect(mockAddToast).toHaveBeenCalledWith({
      type: "INFO",
      message: "Cancelled update of note",
    });

    // Test cancel from remove
    const deleteButton = await screen.findByText("Delete");
    fireEvent.click(deleteButton);
    fireEvent.click(screen.getByText("No"));
    expect(await screen.findByText("Note Type")).toBeInTheDocument();
    expect(mockAddToast).toHaveBeenCalledWith({
      type: "INFO",
      message: "Cancelled removal of note",
    });
  });
});
