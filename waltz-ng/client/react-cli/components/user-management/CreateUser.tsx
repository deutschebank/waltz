import React, { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { useToasts } from "../../context/ToastContext";
import { NotificationTypeEnum } from "../../enums/Notification";
import {
  setSelectedUser,
  setUserRoles,
  setActiveMode,
} from "../../../redux-slices/user-management-slice";
import { userManagementApi } from "../../api/user-management";
import Button from "../common/button/Button";
import reduxStore from "../../../redux-store";
import { Modes } from "../../enums/User";
import { ICreateUser } from "../../types/User";

/**
 * CreateUser component provides a form to register a new user.
 */
const CreateUser: React.FC = () => {
  // State for the new user's username.
  const [userName, setUserName] = useState<string>("");
  // State for the new user's password.
  const [password, setPassword] = useState<string>("");
  // Hook for displaying toast notifications.
  const { addToast } = useToasts();

  // Mutation for handling the user registration API call.
  const registerMutation = useMutation<boolean, Error, ICreateUser>({
    mutationFn: ({ userName, password }: ICreateUser) => {
      const { mutationFn } = userManagementApi.register({
        userName,
        password,
      });
      return mutationFn();
    },
    // On successful registration, fetches the new user's data and navigates to the detail view.
    onSuccess: async (res) => {
      addToast({
        type: NotificationTypeEnum.SUCCESS,
        message: `Successfully registered new user: ${userName}`,
      });
      // After successful registration, fetch the new user's data
      const { queryFn: fetchUserFn } = userManagementApi.getByUserId(userName);
      const registeredUser = await fetchUserFn();
      reduxStore.dispatch(setSelectedUser(registeredUser));
      reduxStore.dispatch(setUserRoles(registeredUser.roles || []));
      reduxStore.dispatch(setActiveMode(Modes.DETAIL));
    },
    // Handles errors during the registration process.
    onError: (error: any) => {
      const message = error.data?.message || error.message || "An unknown error occurred";
      addToast({
        type: NotificationTypeEnum.ERROR,
        message: `Could not create new user: ${message}`,
      });
    },
  });

  // Determines if the registration button should be disabled.
  const disabled = !userName.trim() || !password.trim();

  // Handles the submission of the registration form.
  const handleRegister = () => {
    registerMutation.mutate({ userName, password });
  };

  // Renders the new user registration form.
  return (
    <>
      <h4>New User Registration</h4>
      <div className="help-block">
        Enter a username and password for the new user below
      </div>
      <div className="row">
        <label htmlFor="username" className="col-sm-2">
          User Name
        </label>
        <div className="col-sm-3">
          <input
            className="form-control"
            id="username"
            maxLength={255}
            placeholder="User name"
            value={userName}
            onChange={(e) => setUserName(e.target.value)}
          />
        </div>
      </div>
      <br />
      <div className="row">
        <label htmlFor="password" className="col-sm-2">
          Password
        </label>
        <div className="col-sm-3">
          <input
            className="form-control"
            id="password"
            maxLength={255}
            placeholder="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
      </div>
      <br />
      <div className="row">
        <div className="col-sm-2"></div>
        <div className="col-sm-3">
          <Button
            className="btn btn-success"
            onClick={handleRegister}
            disabled={disabled}
          >
            Register
          </Button>
          <Button
            className="btn btn-skinny"
            onClick={() => reduxStore.dispatch(setActiveMode(Modes.LIST))}
          >
            Cancel
          </Button>
        </div>
      </div>
    </>
  );
};

export default CreateUser;