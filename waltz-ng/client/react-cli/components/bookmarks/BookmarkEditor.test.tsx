import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import BookmarkEditor from "./BookmarkEditor";
import { IBookmark } from "../../types/Bookmark";

describe("BookmarkEditor", () => {
  const mockBookmark: IBookmark = {
      id: 1,
      title: "Waltz",
      url: "https://waltz.example.com",
      description: "Waltz Home",
      isRestricted: false,
      bookmarkKind: "DOCUMENTATION",
      parent: {
          description: "",
          kind: "ACTOR",
          id: 0,
          name: "",
          externalId: "",
          entityLifecycleStatus: "UNKNOWN"
      }
  };

  const mockKinds = [
    { key: "DOCUMENTATION", name: "Documentation" },
    { key: "REPORTING", name: "Reporting" },
  ];

  it("renders the form with initial bookmark data", () => {
    render(
      <BookmarkEditor
        bookmark={mockBookmark}
        kinds={mockKinds}
        doSave={jest.fn()}
        doCancel={jest.fn()}
      />
    );

    expect(screen.getByLabelText("Title")).toHaveValue("Waltz");
    expect(screen.getByLabelText(/URL/)).toHaveValue("https://waltz.example.com");
    expect(screen.getByLabelText("Description")).toHaveValue("Waltz Home");
    expect(screen.getByLabelText("Kind")).toHaveValue("DOCUMENTATION");
    expect(screen.getByLabelText("Is Restricted ?")).not.toBeChecked();
  });

  it("calls doSave with the updated bookmark data on form submission", () => {
    const handleSave = jest.fn();
    render(
      <BookmarkEditor
        bookmark={mockBookmark}
        kinds={mockKinds}
        doSave={handleSave}
        doCancel={jest.fn()}
      />
    );

    fireEvent.change(screen.getByLabelText("Title"), { target: { value: "New Title" } });
    fireEvent.click(screen.getByText("Save"));

    expect(handleSave).toHaveBeenCalledWith(expect.objectContaining({ title: "New Title" }));
  });

  it("calls doCancel when the cancel button is clicked", () => {
    const handleCancel = jest.fn();
    render(
      <BookmarkEditor bookmark={mockBookmark} kinds={mockKinds} doSave={jest.fn()} doCancel={handleCancel} />
    );

    fireEvent.click(screen.getByText("Cancel"));
    expect(handleCancel).toHaveBeenCalled();
  });
});