import React from "react";
import styles from "./EntityNamedNoteRemovalPanel.module.css";
import {NamedNoteType} from "../../types/NamedNote";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import Markdown from "../common/markdown/Markdown";

/**
 * Defines the props for the EntityNamedNoteRemovalPanel component.
 */
export interface EntityNamedNoteRemovalPanelProps {
    note: NamedNoteType | null;
    onConfirm: () => void;
    onCancel: () => void;
}

/**
 * A confirmation panel for removing a named note.
 */
const EntityNamedNoteRemovalPanel: React.FC<EntityNamedNoteRemovalPanelProps> = ({
    note,
    onConfirm,
    onCancel,
}) => {
    return (
        <div className={styles.removalBox}>
            <Icon name="trash" />
            Are you sure you would like to remove this note?
            <div className={styles.quotedNote}>{note && <Markdown text={note.noteText} />}</div>
            <br />
            <Button className="btn btn-danger" onClick={onConfirm}>
                Yes
            </Button>
            <Button className="btn-skinny" onClick={onCancel}>
                No
            </Button>
        </div>
    );
};

export default EntityNamedNoteRemovalPanel;
