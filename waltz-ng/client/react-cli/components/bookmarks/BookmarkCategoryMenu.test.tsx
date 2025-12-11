import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import BookmarkCategoryMenu from "./BookmarkCategoryMenu";

describe("BookmarkCategoryMenu", () => {
  const mockBookmarkKinds = [
    { key: "DOCUMENTATION", name: "Documentation", icon: "book", count: 5 },
    { key: "REPORTING", name: "Reporting", icon: "file-text", count: 3 },
    { key: "SOURCE_CODE", name: "Source Code", icon: "github", count: 0 },
  ];

  it("renders a list of bookmark kinds", () => {
    const handleKindSelect = jest.fn();
    render(
      <BookmarkCategoryMenu
        bookmarkKinds={mockBookmarkKinds}
        onKindSelect={handleKindSelect}
      />
    );

    expect(screen.getByText("Documentation")).toBeInTheDocument();
    expect(screen.getByText("Reporting")).toBeInTheDocument();
    expect(screen.getByText("Source Code")).toBeInTheDocument();
  });

  it("calls onKindSelect with the correct kind when a category is clicked", () => {
    const handleKindSelect = jest.fn();
    render(
      <BookmarkCategoryMenu
        bookmarkKinds={mockBookmarkKinds}
        onKindSelect={handleKindSelect}
      />
    );

    fireEvent.click(screen.getByText("Documentation"));
    expect(handleKindSelect).toHaveBeenCalledWith(mockBookmarkKinds[0]);
  });

  it("toggles selection when a category is clicked twice", () => {
    const handleKindSelect = jest.fn();
    render(
      <BookmarkCategoryMenu
        bookmarkKinds={mockBookmarkKinds}
        onKindSelect={handleKindSelect}
      />
    );

    const documentationButton = screen.getByText("Documentation");

    // Select
    fireEvent.click(documentationButton);
    expect(handleKindSelect).toHaveBeenCalledWith(mockBookmarkKinds[0]);
    // Deselect
    fireEvent.click(documentationButton);
    expect(handleKindSelect).toHaveBeenCalledWith(null);
  });
});