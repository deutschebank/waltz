import React from "react";
import {NoteWithType, Note} from "../../types/NamedNote";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import LastEdited from "../common/LastEdited";
import Markdown from "../common/markdown/Markdown";
import NoData from "../common/no-data/NoData";
import {EntityReference} from "../../types/Entity";

interface EntityNamedNotesGridProps {
  parentEntityRef: EntityReference;
  notesWithTypes: NoteWithType[];
  onEdit: (nt: NoteWithType) => void;
  onDelete: (note: Note) => void;
}

const EntityNamedNotesGrid: React.FC<EntityNamedNotesGridProps> = ({
  parentEntityRef,
  notesWithTypes = [],
  onEdit,
  onDelete,
}) => {
  return (
    <table className="table table-condensed small">
      <colgroup>
        <col width="30%" />
        <col width="70%" />
      </colgroup>
      <thead>
        <tr>
          <th>Note Type</th>
          <th>Note Text</th>
        </tr>
      </thead>
      <tbody>
        {notesWithTypes.length > 0 ? (
          notesWithTypes.map((nt) => (
            <tr key={nt.type?.id} className="waltz-visibility-parent">
              <td>
                <div className="note-title">{nt.type?.name}</div>
                <div className="small text-muted">{nt.type?.description}</div>
                <div className="small text-muted">
                  Last Modified: <LastEdited entity={nt.note} />
                </div>
                {nt.operations && nt.operations.length > 0 && (
                  <div className="actions waltz-visibility-child-30">
                    {nt.operations.includes("UPDATE") && (
                      <Button className="btn-skinny action" onClick={() => onEdit(nt)}>
                        <Icon name="edit" /> Edit
                      </Button>
                    )}
                    {nt.operations.includes("REMOVE") && (
                      <Button
                        className="btn-skinny action"
                        onClick={() => onDelete(nt.note)}
                      >
                        <Icon name="trash" /> Delete
                      </Button>
                    )}
                  </div>
                )}
              </td>
              <td>
                <Markdown context={{ref: parentEntityRef}} text={nt.note.noteText} />
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan={2}>
              <NoData>
                <b>No Notes</b> have been added
              </NoData>
            </td>
          </tr>
        )}
      </tbody>
    </table>
  );
};

export default EntityNamedNotesGrid;
