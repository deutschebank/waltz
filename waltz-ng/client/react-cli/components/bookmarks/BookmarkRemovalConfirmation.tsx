import React from "react";
import styles from "./BookmarkRemovalConfirmation.module.scss";
import Button from "../common/button/Button";

// Defines the props for the BookmarkRemovalConfirmation component.
interface BookmarkRemovalConfirmationProps {
  bookmark: any; // Consider defining a more specific type for bookmark
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
      {/* Confirmation message. */}
      Are you sure you want to remove this bookmark ?
      <div>
        {/* Displays the title and URL of the bookmark to be removed. */}
        <b>{bookmark.title}</b>
        <p>{bookmark.url}</p>
      </div>
      <Button className="btn btn-warning" onClick={doRemove}>
        Remove
      </Button>
      {/* The cancel button simply calls the doCancel function passed in props. */}
      <Button className="btn btn-link" onClick={doCancel}>
        Cancel
      </Button>
    </div>
  );
};

export default BookmarkRemovalConfirmation;
