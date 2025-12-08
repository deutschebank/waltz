import React from "react";
import styles from "./BookmarkRemovalConfirmation.module.scss";
import Button from "../common/button/Button";

interface BookmarkRemovalConfirmationProps {
  bookmark: any; // Consider defining a more specific type for bookmark
  doRemove: () => void;
  doCancel: () => void;
}

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
        <b>{bookmark.title}</b>
        <p>{bookmark.url}</p>
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
