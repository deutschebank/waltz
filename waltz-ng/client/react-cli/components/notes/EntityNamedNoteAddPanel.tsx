import React, {useEffect, useState} from "react";
import styles from "./EntityNamedNoteAddPanel.module.css";
import {NamedNote} from "../../types/NamedNote";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import EntityNamedNoteEditPanel from "./EntityNamedNoteEditPanel";
import {Modes} from "../../enums/User";

/**
 * Defines the props for the EntityNamedNoteAddPanel component.
 */
export interface EntityNamedNoteAddPanelProps {
    availableNoteTypes: NamedNote["entity"][];
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
    // State to manage which view is shown: LIST of note types or EDIT a new note.
    const [mode, setMode] = useState(Modes.LIST);
    // State to hold the type of note the user has selected to create.
    const [selectedType, setSelectedType] = useState<NamedNote["entity"] | null>(null);

    // An empty note object to pass to the edit panel when creating a new note.
    const emptyNote = {noteText: ""};

    /**
     * Switches to the edit mode and sets the selected note type.
     * @param type The note type selected by the user.
     */
    function onShowEditPanel(type: NamedNote["entity"]) {
        setMode(Modes.EDIT);
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
        setMode(Modes.LIST);
        setSelectedType(null);
    }, [availableNoteTypes]);

    return (
        <>
            {/* Render the edit panel when in EDIT mode with a selected type */}
            {mode === Modes.EDIT && selectedType && (
                <EntityNamedNoteEditPanel
                    note={emptyNote}
                    type={selectedType}
                    onSave={handleCreateNote}
                    onCancel={onCancel}
                />
            )}
            {/* Render the list of available note types when in LIST mode */}
            {mode === Modes.LIST && (
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
                                    <button
                                        className="btn-skinny"
                                        onClick={() => onShowEditPanel(type)}
                                    >
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
