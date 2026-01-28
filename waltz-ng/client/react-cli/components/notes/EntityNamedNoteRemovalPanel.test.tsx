import React from "react";
import {render, fireEvent, screen} from "@testing-library/react";
import "@testing-library/jest-dom";
import EntityNamedNoteRemovalPanel from "./EntityNamedNoteRemovalPanel";

describe("EntityNamedNoteRemovalPanel", () => {
  const mockNote = {noteText: "This is a note to be removed."};
  const mockOnConfirm = jest.fn();
  const mockOnCancel = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("should render the removal confirmation message and note text", () => {
    render(
      <EntityNamedNoteRemovalPanel
        note={mockNote}
        onConfirm={mockOnConfirm}
        onCancel={mockOnCancel}
      />
    );

    expect(
      screen.getByText("Are you sure you would like to remove this note?")
    ).toBeInTheDocument();
    expect(screen.getByText("This is a note to be removed.")).toBeInTheDocument();
  });

  it("should call onConfirm when 'Yes' button is clicked", () => {
    render(
      <EntityNamedNoteRemovalPanel
        note={mockNote}
        onConfirm={mockOnConfirm}
        onCancel={mockOnCancel}
      />
    );

    fireEvent.click(screen.getByText("Yes"));
    expect(mockOnConfirm).toHaveBeenCalledTimes(1);
  });

  it("should call onCancel when 'No' button is clicked", () => {
    render(
      <EntityNamedNoteRemovalPanel
        note={mockNote}
        onConfirm={mockOnConfirm}
        onCancel={mockOnCancel}
      />
    );

    fireEvent.click(screen.getByText("No"));
    expect(mockOnCancel).toHaveBeenCalledTimes(1);
  });
});
