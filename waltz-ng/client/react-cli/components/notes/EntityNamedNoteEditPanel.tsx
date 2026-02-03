import React, {useState} from "react";
import styles from "./styles/EntityNamedNoteEditPanel.module.css";
import {Note, NoteTypeEntity} from "../../types/NamedNote";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import Markdown from "../common/markdown/Markdown";

export interface EntityNamedNoteEditPanelProps {
  note: Note;
  type: NoteTypeEntity;
  onSave: (updatedText: string) => void;
  onCancel: () => void;
}

const EntityNamedNoteEditPanel: React.FC<EntityNamedNoteEditPanelProps> = ({
  note,
  type,
  onSave,
  onCancel,
}) => {
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
              <div className={styles.preview} data-testid="note-preview">
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
