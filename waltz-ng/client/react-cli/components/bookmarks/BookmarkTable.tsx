import React from "react";
import BookmarkListItem from "./BookmarkListItem";
import {Action} from "../common/mini-actions/MiniActions";

// Defines the props for the BookmarkTable component.
interface BookmarkTableProps {
  bookmarkGroups: any[];
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
    // Renders a standard HTML table with Bootstrap classes.
    <table className="table table-condensed table-hover">
      {/* Iterates over each group of bookmarks. */}
      {bookmarkGroups.map((group, idx) => (
        <tbody key={idx}>
          {/* Iterates over each bookmark within a group and renders a BookmarkListItem. */}
          {group.value.map((bookmark: any) => (
            <BookmarkListItem key={bookmark.id} actions={actions} bookmark={bookmark} />
          ))}
        </tbody>
      ))}
      {/* Renders any children passed to the component, typically used for a table footer. */}
      {children}
    </table>
  );
};

export default BookmarkTable;
