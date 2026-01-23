import React from "react";
import BookmarkListItem from "./BookmarkListItem";
import {Action} from "../common/mini-actions/MiniActions";
import {BookmarkType, BookmarkGroup} from "../../types/Bookmark";

interface BookmarkTableProps {
  bookmarkGroups: BookmarkGroup[];
  actions: Action[];
  children?: React.ReactNode; // For the footer slot
}

/**
 * Renders a table of bookmarks, organized by groups.
 * Each bookmark is rendered using the BookmarkListItem component.
 */
const BookmarkTable: React.FC<BookmarkTableProps> = ({
  bookmarkGroups = [],
  actions = [],
  children,
}) => {
  return (
    <table className="table table-condensed table-hover">
      {bookmarkGroups.map((group, idx) => (
        <tbody key={idx}>
          {group.value.map((bookmark: BookmarkType) => (
            <BookmarkListItem key={bookmark.id} actions={actions} bookmark={bookmark} />
          ))}
        </tbody>
      ))}
      {children}
    </table>
  );
};

export default BookmarkTable;
