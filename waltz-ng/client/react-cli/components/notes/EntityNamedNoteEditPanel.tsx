import React, {useState} from "react";
import styles from "./EntityNamedNoteEditPanel.module.css";
import {NamedNote, NamedNoteType} from "../../types/NamedNote";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import Markdown from "../common/markdown/Markdown";

/**
 * Defines the props for the EntityNamedNoteEditPanel component.
 */
export interface EntityNamedNoteEditPanelProps {
    note: NamedNoteType;
    type: NamedNote["entity"];
    onSave: (updatedText: string) => void;
    onCancel: () => void;
}

/**
 * A panel for editing a named note.
 */
const EntityNamedNoteEditPanel: React.FC<EntityNamedNoteEditPanelProps> = ({
    note,
    type,
    onSave,
    onCancel,
}) => {
    // State to hold the note text as it's being edited, initialized with the current note text.
    const [working, setWorking] = useState(note.noteText);

    return (
        <div className={styles.editBox}>
            <h4>
                <Icon name="edit" />
                Editing: {type.name}
            </h4>
            <table className="table">
                <colgroup>
                    <col width="50%" />
                    <col width="50%" />
                </colgroup>
                <tbody>
                    <tr>
                        {/* Text area for markdown input, which updates the 'working' state on change. */}
                        <td>
                            <textarea
                                rows={8}
                                placeholder="Enter note text here. Markdown formatting is supported."
                                value={working}
                                onChange={(e) => setWorking(e.target.value)}
                            />
                        </td>
                        {/* Live preview of the markdown text. Shows a placeholder if the input is empty. */}
                        <td style={{verticalAlign: "top"}}>
                            <div className={styles.preview}>
                                <Markdown text={working || "*Preview will appear here*"} />
                            </div>
                        </td>
                    </tr>
                </tbody>
            </table>
            <Button className="btn btn-success" onClick={() => onSave(working)}>
                Save
            </Button>
            <Button className="btn-skinny" onClick={onCancel}>
                Cancel
            </Button>
        </div>
    );
};

export default EntityNamedNoteEditPanel;
