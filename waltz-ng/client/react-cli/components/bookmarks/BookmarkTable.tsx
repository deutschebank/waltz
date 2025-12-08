import React from "react";
import BookmarkListItem from "./BookmarkListItem";
import {Action} from "../common/mini-actions/MiniActions";

interface BookmarkTableProps {
  bookmarkGroups: any[];
  actions: Action[];
  children?: React.ReactNode; // For the footer slot
}

const BookmarkTable: React.FC<BookmarkTableProps> = ({
  bookmarkGroups = [],
  actions = [],
  children,
}) => {
  return (
    <table className="table table-condensed table-hover">
      {bookmarkGroups.map((group, idx) => (
        <tbody key={idx}>
          {group.value.map((bookmark: any) => (
            <BookmarkListItem key={bookmark.id} actions={actions} bookmark={bookmark} />
          ))}
        </tbody>
      ))}
      {children}
    </table>
  );
};

export default BookmarkTable;
