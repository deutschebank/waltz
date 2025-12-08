import React, {useState} from "react";
import _ from "lodash";
import Icon from "../common/Icon";

interface BookmarkCategoryMenuProps {
  bookmarkKinds: any[];
  onKindSelect: (kind: any) => void;
}

const BookmarkCategoryMenu: React.FC<BookmarkCategoryMenuProps> = ({
  bookmarkKinds,
  onKindSelect,
}) => {
  const [selected, setSelected] = useState<any | null>(null);

  const eq = (k1: any, k2: any) => {
    if (_.isNil(k1) || _.isNil(k2)) {
      return false;
    }
    return k1 === k2 || k1.key === k2.key;
  };

  const handleKindSelected = (k: any) => {
    const newSelected = eq(selected, k) ? null : k;
    setSelected(newSelected);
    onKindSelect(newSelected);
  };

  return (
    <ul className="list-group">
      {bookmarkKinds.map((bookmarkKind) => (
        <li
          key={bookmarkKind.key}
          className={`list-group-item ${eq(selected, bookmarkKind) ? "selected" : ""} ${
            bookmarkKind.count === 0 ? "text-muted" : ""
          }`}
        >
          {bookmarkKind.count > 0 ? (
            <button
              className="btn-skinny text-left"
              style={{width: "100%"}}
              onClick={() => handleKindSelected(bookmarkKind)}
            >
              <Icon name={bookmarkKind.icon} /> {bookmarkKind.name}
              {eq(selected, bookmarkKind) && (
                <span className="pull-right">
                  <Icon name="close" />
                </span>
              )}
            </button>
          ) : (
            <span>
              <Icon name={bookmarkKind.icon} /> {bookmarkKind.name}
            </span>
          )}
        </li>
      ))}
    </ul>
  );
};

export default BookmarkCategoryMenu;
