import React from "react";
import styles from "./styles/EntityNamedNoteRemovalPanel.module.css";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import Markdown from "../common/markdown/Markdown";
import {Note} from "../../types/NamedNote";

export interface EntityNamedNoteRemovalPanelProps {
  note: Note | null;
  onConfirm: () => void;
  onCancel: () => void;
}

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
