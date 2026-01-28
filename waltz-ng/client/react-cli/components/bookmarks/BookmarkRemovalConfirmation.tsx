import React from "react";
import styles from "./styles/BookmarkRemovalConfirmation.module.scss";
import Button from "../common/button/Button";
import {BookmarkType} from "../../types/Bookmark";

interface BookmarkRemovalConfirmationProps {
  bookmark: BookmarkType;
  doRemove: () => void;
  doCancel: () => void;
}

/**
 * A confirmation dialog component displayed before a bookmark is removed.
 * It shows the bookmark's title and URL and provides options to confirm or cancel the removal.
 */
const BookmarkRemovalConfirmation: React.FC<BookmarkRemovalConfirmationProps> = ({
  bookmark,
  doRemove,
  doCancel,
}) => {
  return (
    <div className={styles.removalWarning}>
      <h3>Confirm bookmark removal</h3>
      Are you sure you want to remove this bookmark ?
      <div>
        <b>{bookmark?.title}</b>
        <p>{bookmark?.url}</p>
      </div>
      <Button className="btn btn-warning" onClick={doRemove}>
        Remove
      </Button>
      <Button className="btn btn-link" onClick={doCancel}>
        Cancel
      </Button>
    </div>
  );
};

export default BookmarkRemovalConfirmation;
