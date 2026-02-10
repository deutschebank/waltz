import React from "react";
import {render, fireEvent, screen, within} from "@testing-library/react";
import "@testing-library/jest-dom";
import EntityNamedNoteEditPanel from "../EntityNamedNoteEditPanel";
import { NoteTypeEntity } from "../../../types/NamedNote";

describe("EntityNamedNoteEditPanel", () => {
  const mockNote = {noteText: "Initial note text"};
  const mockType: NoteTypeEntity = {
    id: 1,
    name: "Test Note Type",
    description: "A type for testing",
    externalId: "test-type",
    applicableEntityKinds: ["APPLICATION"],
    position: 1,
    isReadOnly: false,
  };
  const mockOnSave = jest.fn();
  const mockOnCancel = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("should render with initial values", () => {
    render(
      <EntityNamedNoteEditPanel
        note={mockNote}
        type={mockType}
        onSave={mockOnSave}
        onCancel={mockOnCancel}
      />
    );

    expect(screen.getByText("Editing: Test Note Type")).toBeInTheDocument();
    expect(screen.getByDisplayValue("Initial note text")).toBeInTheDocument();
  });

  it("should update the working text on textarea change", () => {
    render(
      <EntityNamedNoteEditPanel
        note={mockNote}
        type={mockType}
        onSave={mockOnSave}
        onCancel={mockOnCancel}
      />
    );

    const textarea = screen.getByDisplayValue("Initial note text");
    fireEvent.change(textarea, {target: {value: "Updated text"}});

    expect(textarea).toHaveValue("Updated text");

    const preview = screen.getByTestId("note-preview");
    expect(within(preview).getByText("Updated text")).toBeInTheDocument();
  });

  it("should call onSave with the updated text when save button is clicked", () => {
    render(
      <EntityNamedNoteEditPanel
        note={mockNote}
        type={mockType}
        onSave={mockOnSave}
        onCancel={mockOnCancel}
      />
    );

    const textarea = screen.getByDisplayValue("Initial note text");
    fireEvent.change(textarea, {target: {value: "Updated text"}});
    fireEvent.click(screen.getByText("Save"));

    expect(mockOnSave).toHaveBeenCalledWith("Updated text");
  });
});
