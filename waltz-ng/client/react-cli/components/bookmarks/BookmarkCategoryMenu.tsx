import React, {useState} from "react";
import Icon from "../common/Icon";
import { BookmarkKinds } from "../../types/Bookmark";


// Defines the props for the BookmarkCategoryMenu component.
interface BookmarkCategoryMenuProps {
  bookmarkKinds: BookmarkKinds[];
  onKindSelect: (kind: BookmarkKinds | null) => void; 
}

/**
 * Renders a menu of bookmark categories that can be used for filtering.
 */
const BookmarkCategoryMenu: React.FC<BookmarkCategoryMenuProps> = ({
  bookmarkKinds,
  onKindSelect,
}) => {
  // State to keep track of the currently selected category.
  const [selected, setSelected] = useState<BookmarkKinds | null>(null);

  /**
   * Compares two bookmark kinds for equality.
   * They are considered equal if they are the same object or have the same 'key' property.
   */
  const eq = (k1: BookmarkKinds | null, k2: BookmarkKinds | null) => {
    if (k1 === null || k2 === null) { // Handles cases where one or both kinds are null.
      return false;
    }
    return k1 === k2 || k1.key === k2.key;
  };

  // Handles the click event on a bookmark category.
  const handleKindSelected = (k: BookmarkKinds) => {
    // Toggles the selection: deselects if the same kind is clicked again, otherwise selects the new kind.
    const newSelected = eq(selected, k) ? null : k;
    setSelected(newSelected);
    onKindSelect(newSelected);
  };

  return (
    <ul className="list-group">
      {/* Maps over the bookmark kinds to render a list item for each. */}
      {bookmarkKinds.map((bookmarkKind) => (
        <li
          key={bookmarkKind.key}
          className={`list-group-item ${eq(selected, bookmarkKind) ? "selected" : ""} ${
            bookmarkKind.count === 0 ? "text-muted" : ""
          }`}
        >
          {bookmarkKind.count > 0 ? (
            // If there are bookmarks of this kind, render a clickable button.
            <button
              className="btn-skinny text-left"
              style={{width: "100%"}}
              onClick={() => handleKindSelected(bookmarkKind)}
            >
              <Icon name={bookmarkKind.icon} /> {bookmarkKind.name}
              {eq(selected, bookmarkKind) && (
                // Shows a close icon if the category is currently selected.
                <span className="pull-right">
                  <Icon name="close" />
                </span>
              )}
            </button>
          ) : (
            <span>
              {/* If there are no bookmarks of this kind, render non-clickable text. */}
              <Icon name={bookmarkKind.icon} /> {bookmarkKind.name}
            </span>
          )}
        </li>
      ))}
    </ul>
  );
};

export default BookmarkCategoryMenu;
