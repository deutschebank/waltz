import React, {useEffect, useState} from "react";
import styles from "./styles/EntityNamedNoteAddPanel.module.css";
import {NoteTypeEntity} from "../../types/NamedNote";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import EntityNamedNoteEditPanel from "./EntityNamedNoteEditPanel";
import {VisualStateModes} from "../../enums/VisualState";

export interface EntityNamedNoteAddPanelProps {
  availableNoteTypes: NoteTypeEntity[];
  onCreate: (newNote: {noteText: string; noteTypeId: number}) => void;
  onCancel: () => void;
}

/**
 * A panel for adding a new named note. It first lists available note types
 * and then shows an edit panel for the selected type.
 */
const EntityNamedNoteAddPanel: React.FC<EntityNamedNoteAddPanelProps> = ({
  availableNoteTypes = [],
  onCreate,
  onCancel,
}) => {
  const [mode, setMode] = useState(VisualStateModes.LIST);
  const [selectedType, setSelectedType] = useState<NoteTypeEntity | null>(null);

  // An empty note object to pass to the edit panel when creating a new note.
  const emptyNote = {noteText: ""};

  function onShowEditPanel(type: NoteTypeEntity) {
    setMode(VisualStateModes.EDIT);
    setSelectedType(type);
  }

  /**
   * Creates a new note object and calls the onCreate prop.
   * @param updatedText The text content of the new note.
   */
  function handleCreateNote(updatedText: string) {
    if (selectedType) {
      const newEvt = {
        noteText: updatedText,
        noteTypeId: selectedType.id,
      };
      onCreate(newEvt);
    }
  }

  // Effect to reset the component's state when the available note types change.
  useEffect(() => {
    setMode(VisualStateModes.LIST);
    setSelectedType(null);
  }, [availableNoteTypes]);

  return (
    <>
      {/* Render the edit panel when in EDIT mode with a selected type */}
      {mode === VisualStateModes.EDIT && selectedType && (
        <EntityNamedNoteEditPanel
          note={emptyNote}
          type={selectedType}
          onSave={handleCreateNote}
          onCancel={onCancel}
        />
      )}
      {/* Render the list of available note types when in LIST mode */}
      {mode === VisualStateModes.LIST && (
        <div className={styles.editBox}>
          <h4>
            <Icon name="plus" />
            Add a new note:
          </h4>
          The following note types are available:
          <br />
          <dl className={styles.typeList}>
            {availableNoteTypes.map((type) => (
              <React.Fragment key={type.id}>
                <dt>
                  <button className="btn-skinny" onClick={() => onShowEditPanel(type)}>
                    <Icon name="sticky-note-o" /> {type.name}
                  </button>
                </dt>
                <dd>
                  <div className="small text-muted">{type.description}</div>
                </dd>
              </React.Fragment>
            ))}
          </dl>
          <Button className="btn-skinny" onClick={onCancel}>
            Cancel
          </Button>
        </div>
      )}
    </>
  );
};

export default EntityNamedNoteAddPanel;
