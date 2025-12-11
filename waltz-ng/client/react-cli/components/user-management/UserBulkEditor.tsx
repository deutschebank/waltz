import React, { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { userManagementApi } from "../../api/user-management";
import { useToasts } from "../../context/toast/ToastContext";
import { NotificationTypeEnum } from "../../enums/Notification";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import { UserBulkResponse } from "../../types/User";

// Defines the modes for bulk uploading user roles.
const UploadModes = {
  ADD: "ADD_ONLY",
  REMOVE: "REMOVE_ONLY",
  REPLACE: "REPLACE",
};

// Defines the different view modes for the component.
const Modes = {
  EDIT: "EDIT",
  PREVIEW: "PREVIEW",
  RESULT: "RESULT",
};

// Maps upload mode keys to user-friendly labels.
const uploadModeLabels: { [key: string]: string } = {
  ADD: "Add Only",
  REMOVE: "Remove Only",
  REPLACE: "Replace",
};

// Defines the structure for a preview row.
interface PreviewRow {
  givenUser: string;
  givenRole: string;
  givenComment: string;
  resolvedUser: string | null;
  resolvedRole: string | null;
  resolvedComment: string | null;
}

/**
 * UserBulkEditor provides a UI for bulk editing user roles via CSV data.
 */
const UserBulkEditor: React.FC = () => {
  // Hook for displaying toast notifications.
  const { addToast } = useToasts();
  // State for the selected upload mode (e.g., ADD, REMOVE, REPLACE).
  const [uploadMode, setUploadMode] = useState<string>(UploadModes.ADD);
  // State for the raw CSV data input by the user.
  const [rowData, setRowData] = useState<string>("");
  // State for storing the preview data returned from the server.
  const [preview, setPreview] = useState<PreviewRow[]>([]);
  // State to control the current view mode (EDIT, PREVIEW, RESULT).
  const [mode, setMode] = useState<string>(Modes.EDIT);
  // State to store the count of updated entries after a successful upload.
  const [updatedCount, setUpdatedCount] = useState<number>(0);

  // Mutation for fetching a preview of the bulk upload.
  const { mutate: previewMutation, isPending: isPreviewing } = useMutation<
    UserBulkResponse<PreviewRow[]>,
    Error
  >({
    mutationFn: () => {
      const { mutationFn } = userManagementApi.bulkUploadPreview(uploadMode, rowData);
      return mutationFn();
    },
    onSuccess: (response) => {
      setPreview(response.data);
      setMode(Modes.PREVIEW);
    },
    onError: (error: any) => {
      const message = error.data?.message || error.message;
      addToast({
        type: NotificationTypeEnum.ERROR,
        message: `Could not preview bulk upload: ${message}`,
      });
    },
  });

  // Mutation for performing the bulk upload of user roles.
  const { mutate: uploadMutation, isPending: isUploading } = useMutation<
    UserBulkResponse<number>,
    Error
  >({
    mutationFn: () => {
      const { mutationFn } = userManagementApi.bulkUpload(uploadMode, rowData);
      return mutationFn();
    },
    onSuccess: (res) => {
      setUpdatedCount(res.data);
      setMode(Modes.RESULT);
    },
    onError: (error: any) => {
      const message =
        error.data?.message || error.message || "Could not perform bulk upload";
      addToast({
        type: NotificationTypeEnum.ERROR,
        message: `Could not perform bulk upload: ${message}`,
      });
    },
  });

  /**
   * Renders the form for inputting user role data.
   */
  const renderEditMode = () => (
    <form
      autoComplete="off"
      onSubmit={(e) => {
        e.preventDefault();
        previewMutation();
      }}
    >
      <fieldset>
        <legend>Upload Mode</legend>
        <div className="btn-group">
          {(Object.keys(UploadModes) as Array<keyof typeof UploadModes>).map((key) => (
            <div className="radio" key={key}>
              <label>
                <input
                  style={{ display: "inline-block" }}
                  type="radio"
                  checked={uploadMode === UploadModes[key]}
                  name="uploadMode"
                  value={UploadModes[key]}
                  onChange={(e) => setUploadMode(e.target.value)}
                />
                {uploadModeLabels[key]}
              </label>
              <div className="help-block">
                {key === "ADD" &&
                  "Add will add the roles specified in the upload section to any existing roles for the user."}
                {key === "REMOVE" &&
                  "Remove will only remove the roles specified in the upload section."}
                {key === "REPLACE" &&
                  "Replace will remove all existing roles for the user, and replace them with the uploaded roles."}
              </div>
            </div>
          ))}
        </div>
      </fieldset>
      <br />
      <fieldset>
        <legend>User/Role Data</legend>
        <div className="form-group">
          <div className="help-block">
            This section allows you to bulk edit user roles.
          </div>
          <div className="row">
            <div className="col-md-7">
              <textarea
                id="row-data"
                placeholder="username, role, comment"
                value={rowData}
                onChange={(e) => setRowData(e.target.value)}
                cols={120}
                className="form-control"
                rows={10}
              />
            </div>
            <div className="col-md-5">
              <div className="help-block">
                Each row should reflect a user/role/comment combination, using either
                commas or tabs as delimiters. For example:
              </div>
              <pre style={{ whiteSpace: "pre-line" }}>
                username, role, comment{"\n"}
                test.user@somewhere.com, BOOKMARK_EDITOR, comment{"\n"}
                test.user@somewhere.com, ADMIN, comment{"\n"}
                another.user@somewhere.com, ADMIN, comment
              </pre>
            </div>
          </div>
        </div>
      </fieldset>
      <Button
        type="submit"
        className="btn btn-success"
        disabled={!rowData || isPreviewing}
        data-testid="preview-button"
      >
        {isPreviewing ? "Previewing..." : "Preview"}
      </Button>
    </form>
  );

  /**
   * Renders the preview of the bulk upload before confirmation.
   */
  const renderPreviewMode = () => (
    <form
      autoComplete="off"
      onSubmit={(e) => {
        e.preventDefault();
        uploadMutation();
      }}
    >
      <fieldset>
        <legend>Preview</legend>
        <div className="help-block">
          This section shows a preview of the results of the bulk upload. Red cells
          indicate that the user or role could not be found and the line will be ignored
          if you proceed.
        </div>
        <label htmlFor="mode-preview">Upload Mode:</label>
        <div id="mode-preview">{uploadMode}</div>
        <br />
        <label htmlFor="preview-table">User/Role Data:</label>
        <table id="preview-table" className="table table-condensed small">
          <thead>
            <tr>
              <th>Username</th>
              <th>Role</th>
              <th>Comment</th>
            </tr>
          </thead>
          <tbody>
            {preview.length > 0 &&
              preview.map((row, index) => {
                const okUser = !!row.resolvedUser;
                const okRole = !!row.resolvedRole;
                const okComment = !!row.resolvedComment;
                return (
                  <tr key={index}>
                    <td className={okUser ? "success" : "danger"}>
                      {row.givenUser}{" "}
                      <Icon name={okUser ? "check" : "exclamation-triangle"} />
                    </td>
                    <td className={okRole ? "success" : "danger"}>
                      {row.givenRole}{" "}
                      <Icon name={okRole ? "check" : "exclamation-triangle"} />
                    </td>
                    <td className={okComment ? "success" : "danger"}>
                      {row.givenComment}{" "}
                      <Icon name={okComment ? "check" : "exclamation-triangle"} />
                    </td>
                  </tr>
                );
              })}
          </tbody>
        </table>
      </fieldset>
      <Button
        type="submit"
        className="btn btn-success"
        disabled={!rowData || isUploading}
        data-testid="update-button"
      >
        {isUploading ? "Updating..." : "Update"}
      </Button>
      <Button
        className="btn btn-skinny"
        data-testid="back-preview"
        onClick={() => setMode(Modes.EDIT)}
      >
        Back
      </Button>
    </form>
  );

  /**
   * Renders the result summary after the bulk upload is complete.
   */
  const renderResultMode = () => (
    <>
      <fieldset>
        <legend>Result</legend>
        <div>Updated: {updatedCount} user/role entries.</div>
      </fieldset>
      <br />
      <Button
        className="btn btn-skinny"
        data-testid="back-result"
        onClick={() => setMode(Modes.EDIT)}
      >
        Back
      </Button>
    </>
  );

  // Determines which mode to render based on the current state.
  switch (mode) {
    case Modes.PREVIEW:
      return renderPreviewMode();
    case Modes.RESULT:
      return renderResultMode();
    case Modes.EDIT:
    default:
      return renderEditMode();
  }
};

export default UserBulkEditor;