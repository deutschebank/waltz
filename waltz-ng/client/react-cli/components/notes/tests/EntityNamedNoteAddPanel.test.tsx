import React from "react";
import {render, fireEvent, screen} from "@testing-library/react";
import "@testing-library/jest-dom";
import EntityNamedNoteAddPanel from "../EntityNamedNoteAddPanel";
import {NoteTypeEntity} from "../../../types/NamedNote";

describe("EntityNamedNoteAddPanel", () => {
  const mockAvailableNoteTypes: NoteTypeEntity[] = [
    {
      id: 1,
      name: "Type 1",
      description: "Description 1",
      externalId: "EXT1",
      applicableEntityKinds: ["APPLICATION"],
      position: 1,
      isReadOnly: false,
    },
    {
      id: 2,
      name: "Type 2",
      description: "Description 2",
      externalId: "EXT2",
      applicableEntityKinds: ["APPLICATION"],
      position: 2,
      isReadOnly: false,
    },
  ];

  const mockOnCreate = jest.fn();
  const mockOnCancel = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  it("should render the list of available note types", () => {
    render(
      <EntityNamedNoteAddPanel
        availableNoteTypes={mockAvailableNoteTypes}
        onCreate={mockOnCreate}
        onCancel={mockOnCancel}
      />
    );

    expect(screen.getByText("Add a new note:")).toBeInTheDocument();
    expect(screen.getByText("Type 1")).toBeInTheDocument();
    expect(screen.getByText("Type 2")).toBeInTheDocument();
  });

  it("should switch to the edit panel when a note type is clicked", () => {
    render(
      <EntityNamedNoteAddPanel
        availableNoteTypes={mockAvailableNoteTypes}
        onCreate={mockOnCreate}
        onCancel={mockOnCancel}
      />
    );

    fireEvent.click(screen.getByText("Type 1"));

    expect(screen.getByText("Editing: Type 1")).toBeInTheDocument();
  });

  it("should call onCancel when the cancel button is clicked in list view", () => {
    render(
      <EntityNamedNoteAddPanel
        availableNoteTypes={mockAvailableNoteTypes}
        onCreate={mockOnCreate}
        onCancel={mockOnCancel}
      />
    );

    fireEvent.click(screen.getAllByText("Cancel")[0]);
    expect(mockOnCancel).toHaveBeenCalledTimes(1);
  });

  it("should call onCreate when a new note is saved", () => {
    render(
      <EntityNamedNoteAddPanel
        availableNoteTypes={mockAvailableNoteTypes}
        onCreate={mockOnCreate}
        onCancel={mockOnCancel}
      />
    );

    // Switch to edit mode
    fireEvent.click(screen.getByText("Type 1"));

    // Interact with the edit panel
    const textarea = screen.getByPlaceholderText(
      "Enter note text here. Markdown formatting is supported."
    );
    fireEvent.change(textarea, {target: {value: "New note text"}});
    fireEvent.click(screen.getByText("Save"));

    expect(mockOnCreate).toHaveBeenCalledWith({
      noteText: "New note text",
      noteTypeId: 1,
    });
  });
});
