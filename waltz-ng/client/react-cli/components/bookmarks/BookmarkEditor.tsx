import React, {useState} from "react";
import styles from "./styles/BookmarkEditor.module.scss";
import Button from "../common/button/Button";
import {IBookmark} from "../../types/Bookmark";

// Defines the props for the BookmarkEditor component.
interface BookmarkEditorProps {
  doCancel: () => void;
  doSave: (bookmark: IBookmark) => void;
  bookmark: IBookmark;
  kinds: any[];
}

/**
 * A form component for creating and editing a bookmark.
 */
const BookmarkEditor: React.FC<BookmarkEditorProps> = ({
  doCancel,
  doSave,
  bookmark,
  kinds,
}) => {
  // State to hold the form's data, initialized with the bookmark prop.
  const [workingCopy, setWorkingCopy] = useState({...bookmark});

  // Handles the form submission.
  const handleSave = (e: React.FormEvent) => {
    e.preventDefault();
    doSave(workingCopy);
  };

  /**
   * Handles changes to form inputs and updates the workingCopy state.
   * It supports various input types, including text, select, textarea, and checkbox.
   */
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>
  ) => {
    const {name, value} = e.target;

    if (e.target.type === "checkbox") {
      const {checked} = e.target as HTMLInputElement;
      setWorkingCopy((prev) => ({...prev, [name]: checked}));
    } else {
      setWorkingCopy((prev) => ({...prev, [name]: value}));
    }
  };

  return (
    <>
      {/* The title of the form changes based on whether it's a new or existing bookmark. */}
      <h4>Bookmark Edit</h4>
      <form autoComplete="off" onSubmit={handleSave}>
        <div className="form-group">
          <label htmlFor="title">Title</label>
          <input
            className="form-control"
            id="title"
            name="title"
            placeholder="Link Title"
            value={workingCopy.title || ""}
            onChange={handleChange}
          />
        </div>

        <div className="form-group">
          <label htmlFor="url">
            URL <small className="text-muted">(required)</small>
          </label>
          <input
            className={`form-control ${styles.input}`}
            id="url"
            name="url"
            required
            placeholder="URL"
            value={workingCopy.url || ""}
            onChange={handleChange}
          />
        </div>

        <div className="checkbox">
          <label>
            <input
              type="checkbox"
              name="isRestricted"
              checked={workingCopy.isRestricted || false}
              onChange={handleChange}
            />
            Is Restricted ?
          </label>
          <div className="help-block">
            Use this flag to indicate if visitors may need special permissions to access
            the link
          </div>
        </div>

        <div className="form-group">
          <label htmlFor="kind">Kind</label>
          <select
            // The 'name' attribute must match the property name in the workingCopy state.
            id="kind"
            name="bookmarkKind"
            value={workingCopy.bookmarkKind}
            className="form-control"
            onChange={handleChange}
          >
            {kinds.map((kind) => (
              <option key={kind.key} value={kind.key}>
                {kind.name}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="description">Description</label>
          <textarea
            className="form-control"
            id="description"
            name="description"
            value={workingCopy.description || ""}
            onChange={handleChange}
          />
        </div>

        {/* Button to submit the form and save the bookmark. */}
        <Button type="submit" className="btn btn-success">
          Save
        </Button>

        <Button className="btn btn-link" onClick={doCancel}>
          {/* Button to cancel the edit and close the form. */}
          Cancel
        </Button>
      </form>
    </>
  );
};

export default BookmarkEditor;
