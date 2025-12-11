import React from "react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import {
  setActiveMode,
  setSelectedUser,
  setUserRoles,
} from "../../../redux-slices/user-management-slice";
import reduxStore from "../../../redux-store";
import { userManagementApi } from "../../api/user-management";
import { useToasts } from "../../context/toast/ToastContext";
import { NotificationTypeEnum } from "../../enums/Notification";
import NoData from "../common/no-data/NoData";
import Button from "../common/button/Button";
import { useSliceSelector } from "../../hooks/useSliceSelector";
import { Modes } from "../../enums/User";

/**
 * DeleteUser component provides a confirmation dialog for deleting a user.
 */
const DeleteUser: React.FC = () => {
  // Retrieves the currently selected user from the Redux store.
  const selectedUser = useSliceSelector((state) => state.userManagement.selectedUser);
  // Hook for displaying toast notifications.
  const { addToast } = useToasts();
  // Hook to access the query client for cache invalidation.
  const queryClient = useQueryClient();

  // Mutation for handling the user deletion API call.
  const { mutate: deleteUserMutation } = useMutation<boolean, Error, string>({
    mutationFn: (username: string) => {
      const { mutationFn } = userManagementApi.deleteUser(username);
      return mutationFn();
    },
    // On successful deletion, shows a success toast, clears user state, and refetches the user list.
    onSuccess: (res, username) => {
      addToast({
        type: NotificationTypeEnum.SUCCESS,
        message: `Successfully deleted user: ${username}`,
      });
      reduxStore.dispatch(setSelectedUser(null));
      reduxStore.dispatch(setUserRoles([]));
      reduxStore.dispatch(setActiveMode(Modes.LIST));
      // Invalidate the user list query to refetch the data
      queryClient.invalidateQueries({
        queryKey: ["user", "findAll"],
      });
    },
    // Handles errors during the deletion process.
    onError: (error: any) => {
      const message = error.data?.message || error.message || "An unknown error occurred";
      addToast({
        type: NotificationTypeEnum.ERROR,
        message: `Failed to delete user: ${message}`,
      });
    },
  });

  // Renders a message if no user is selected.
  if (!selectedUser) {
    return <NoData>No user selected</NoData>;
  }

  // Handles the confirmation of the delete action.
  const handleDelete = () => {
    deleteUserMutation(selectedUser.userName);
  };

  // Renders the delete confirmation message and buttons.
  return (
    <>
      <h4>Are you sure you want to delete user: {selectedUser.userName}?</h4>
      <br />
      <Button className="btn btn-danger" onClick={handleDelete}>
        Delete
      </Button>
      <Button
        className="btn btn-skinny"
        onClick={() => reduxStore.dispatch(setActiveMode(Modes.DETAIL))}
      >
        Cancel
      </Button>
    </>
  );
};

export default DeleteUser;
