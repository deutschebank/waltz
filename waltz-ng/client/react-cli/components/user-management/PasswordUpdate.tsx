import React, { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { setActiveMode } from "../../../redux-slices/user-management-slice";
import { userManagementApi } from "../../api/user-management";
import Button from "../common/button/Button";
import NoData from "../common/no-data/NoData";
import { useSliceSelector } from "../../hooks/useSliceSelector";
import reduxStore from "../../../redux-store";
import { useToasts } from "../../context/toast/ToastContext";
import { NotificationTypeEnum } from "../../enums/Notification";
import { VisualStateModes } from "../../enums/VisualState";

const PasswordUpdate: React.FC = () => {
  const selectedUser = useSliceSelector((state) => state.userManagement.selectedUser);
  const { addToast } = useToasts();
  const [newPassword, setNewPassword] = useState("");
  const [passwordCheck, setPasswordCheck] = useState("");

  // Mutation for handling the password reset API call.
  const { mutate: resetPasswordMutation } = useMutation<boolean, Error, string>({
    mutationFn: (password: string) => {
      if (!selectedUser) {
        throw new Error("No user selected");
      }
      // The API expects currentPassword, but as an admin reset, we pass null.
      const { mutationFn } = userManagementApi.resetPassword(
        selectedUser.userName,
        password,
        null
      );
      return mutationFn();
    },
    onSuccess: (res) => {
      addToast({
        type: NotificationTypeEnum.SUCCESS,
        message: `Successfully updated password for user: ${selectedUser?.userName}`,
      });
      // navigates back to the detail view.
      reduxStore.dispatch(setActiveMode(VisualStateModes.DETAIL));
    },
    onError: (error: any) => {
      const message = error.data?.message || error.message || "An unknown error occurred";
      addToast({
        type: NotificationTypeEnum.ERROR,
        message: `Could not update password: ${message}`,
      });
    },
  });

  if (!selectedUser) {
    return <NoData>No user selected to update password for.</NoData>;
  }

  const disabled =
    !newPassword || newPassword.trim() === "" || newPassword !== passwordCheck;

  const handleResetPassword = () => {
    resetPasswordMutation(newPassword);
  };

  // Renders the password update form.
  return (
    <>
      <h4>Password Update for {selectedUser.userName}</h4>
      <div className="help-block">
        Enter a new password for the user below, password cannot be blank and the tr-typed
        password must match (case-sensitive).
      </div>

      <div className="row">
        <label htmlFor="new" className="col-sm-2">
          New Password
        </label>
        <div className="col-sm-3">
          <input
            className="form-control"
            id="new"
            maxLength={255}
            placeholder="New password"
            value={newPassword}
            onChange={(e) => setNewPassword(e.target.value)}
          />
        </div>
      </div>
      <br />
      <div className="row">
        <label htmlFor="check" className="col-sm-2">
          Re-type Password
        </label>
        <div className="col-sm-3">
          <input
            className="form-control"
            id="check"
            maxLength={255}
            placeholder="New password"
            value={passwordCheck}
            onChange={(e) => setPasswordCheck(e.target.value)}
          />
        </div>
      </div>

      <br />
      <div className="row">
        <div className="col-sm-2"></div>
        <div className="col-sm-3">
          <Button
            className="btn btn-danger"
            onClick={handleResetPassword}
            disabled={disabled}
          >
            Reset Password
          </Button>
          <Button
            className="btn btn-skinny"
            onClick={() => reduxStore.dispatch(setActiveMode(VisualStateModes.DETAIL))}
          >
            Cancel
          </Button>
        </div>
      </div>
    </>
  );
};

export default PasswordUpdate;
