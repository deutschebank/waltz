import React from "react";
import {render, screen, fireEvent} from "@testing-library/react";
import "@testing-library/jest-dom";
import BookmarkRemovalConfirmation from "./BookmarkRemovalConfirmation";
import {BookmarkType} from "../../types/Bookmark";

describe("BookmarkRemovalConfirmation", () => {
  const mockBookmark: BookmarkType = {
    id: 1,
    title: "Waltz Home",
    url: "https://waltz.example.com",
    description: "Waltz Home Description",
    isRestricted: false,
    bookmarkKind: "DOCUMENTATION",
    parent: {
      description: "",
      kind: "ACTOR",
      id: 0,
      name: "",
      externalId: "",
      entityLifecycleStatus: "UNKNOWN",
    },
  };

  it("renders the confirmation message with bookmark details", () => {
    render(
      <BookmarkRemovalConfirmation
        bookmark={mockBookmark}
        doRemove={jest.fn()}
        doCancel={jest.fn()}
      />
    );

    expect(screen.getByText("Confirm bookmark removal")).toBeInTheDocument();
    expect(screen.getByText("Waltz Home")).toBeInTheDocument();
    expect(screen.getByText("https://waltz.example.com")).toBeInTheDocument();
  });

  it("calls doRemove when the remove button is clicked", () => {
    const handleRemove = jest.fn();
    render(
      <BookmarkRemovalConfirmation
        bookmark={mockBookmark}
        doRemove={handleRemove}
        doCancel={jest.fn()}
      />
    );
    fireEvent.click(screen.getByText("Remove"));
    expect(handleRemove).toHaveBeenCalled();
  });

  it("calls doCancel when the cancel button is clicked", () => {
    const handleCancel = jest.fn();
    render(
      <BookmarkRemovalConfirmation
        bookmark={mockBookmark}
        doRemove={jest.fn()}
        doCancel={handleCancel}
      />
    );
    fireEvent.click(screen.getByText("Cancel"));
    expect(handleCancel).toHaveBeenCalled();
  });
});
