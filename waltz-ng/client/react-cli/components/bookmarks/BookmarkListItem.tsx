import React from "react";
import _ from "lodash";
import Icon from "../common/Icon";
import MiniActions, {Action} from "../common/mini-actions/MiniActions";
import LastEdited from "../common/LastEdited";
import {IBookmark} from "../../types/Bookmark";

interface BookmarkListItemProps {
  bookmark: IBookmark;
  actions?: Action[];
}

const BookmarkListItem: React.FC<BookmarkListItemProps> = ({bookmark, actions = []}) => {
  return (
    <tr className="waltz-visibility-parent">
      <td style={{verticalAlign: "middle"}}>
        <Icon name={bookmark.icon} />
      </td>
      <td>
        <a href={bookmark.url}>{bookmark.title || bookmark.domain}</a>
        {bookmark.title && <small className="text-muted"> ({bookmark.domain})</small>}
        {bookmark.isRestricted && (
          <small className="text-muted">
            <Icon name="lock" /> Permissions may be required
          </small>
        )}
        <div className={`text-muted ${_.isNil(bookmark.description) ? "italics" : ""}`}>
          {bookmark.description || "No description provided"}
        </div>
      </td>
      <td
        className="waltz-visibility-child-20"
        style={{textAlign: "right", paddingRight: "1em"}}
      >
        <MiniActions ctx={bookmark} actions={actions} />
        <div className="text-muted small">
          Last updated: <LastEdited entity={bookmark} />
        </div>
      </td>
    </tr>
  );
};

export default BookmarkListItem;
