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
    const [mode, setMode] = useState(Modes.LIST);
    const [selectedType, setSelectedType] = useState<NamedNote["entity"] | null>(null);

    const emptyNote = {noteText: ""};

    function onShowEditPanel(type: NamedNote["entity"]) {
        setMode(Modes.EDIT);
        setSelectedType(type);
    }

    function handleCreateNote(updatedText: string) {
        if (selectedType) {
            const newEvt = {
                noteText: updatedText,
                noteTypeId: selectedType.id,
            };
            onCreate(newEvt);
        }
    }

    useEffect(() => {
        setMode(Modes.LIST);
        setSelectedType(null);
    }, [availableNoteTypes]);

    return (
        <>
            {mode === Modes.EDIT && selectedType && (
                <EntityNamedNoteEditPanel
                    note={emptyNote}
                    type={selectedType}
                    onSave={handleCreateNote}
                    onCancel={onCancel}
                />
            )}
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
