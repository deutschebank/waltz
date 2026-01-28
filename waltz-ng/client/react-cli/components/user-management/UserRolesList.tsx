import React, { useState, useMemo, useEffect } from "react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { setActiveMode, setUserRoles } from "../../../redux-slices/user-management-slice";
import { roleApi } from "../../api/roles";
import { userManagementApi } from "../../api/user-management";
import SearchInput from "../common/SearchInput";
import { termSearch } from "../../../common";
import { useToasts } from "../../context/toast/ToastContext";
import { NotificationTypeEnum } from "../../enums/Notification";
import NoData from "../common/no-data/NoData";
import Button from "../common/button/Button";
import Icon from "../common/Icon";
import { useSliceSelector } from "../../hooks/useSliceSelector";
import reduxStore from "../../../redux-store";
import { VisualStateModes } from "../../enums/VisualState";
import { UserRoles } from "../../enums/User";

const UserRolesList: React.FC = () => {
  const selectedUser = useSliceSelector((state) => state.userManagement.selectedUser);
  const userRoles = useSliceSelector((state) => state.userManagement.userRoles);
  const { addToast } = useToasts();
  const [qry, setQry] = useState("");
  const [comment, setComment] = useState("");
  const [expandedReadOnly, setExpandedReadOnly] = useState(false);

  // Fetches all available roles from the API.
  const { data: allRoles = [] } = useQuery({
    ...roleApi.findAll(),
  });

  // Mutation for updating user roles.
  const { mutate: updateUserRolesMutation } = useMutation<number, Error>({
    mutationFn: () => {
      // Throws an error if no user is selected.
      if (!selectedUser) throw new Error("No user selected");
      const { mutationFn } = userManagementApi.updateRoles(
        selectedUser.userName,
        userRoles,
        comment
      );
      return mutationFn();
    },
    // On success, shows a success toast and returns to the user list.
    onSuccess: (res) => {
      addToast({
        type: NotificationTypeEnum.SUCCESS,
        message: `Successfully updated roles for ${selectedUser?.userName}`,
      });
      reduxStore.dispatch(setActiveMode(VisualStateModes.LIST));
    },
    // On error, shows an error toast.
    onError: (error: any) => {
      const message = error.data?.message || error.message || "An unknown error occurred";
      addToast({
        type: NotificationTypeEnum.ERROR,
        message: `Unable to update roles: ${message}`,
      });
    },
  });

  // Memoized separation of roles into user-selectable and read-only.
  const [userSelectableRoles, readOnlyRoles] = useMemo(() => {
    const selectable = allRoles.filter((r) => r.userSelectable);
    const nonSelectable = allRoles.filter((r) => !r.userSelectable);
    return [selectable, nonSelectable];
  }, [allRoles]);

  // Memoized list of read-only roles assigned to the selected user.
  const userReadOnlyRoles = useMemo(() => {
    return readOnlyRoles.filter((d) => selectedUser?.roles.includes(d.key));
  }, [readOnlyRoles, selectedUser]);

  // Memoized list of roles to display based on the search query.
  const displayedRoles = useMemo(() => {
    return qry
      ? termSearch(userSelectableRoles, qry, ["name", "key", "value", "description"])
      : userSelectableRoles;
  }, [userSelectableRoles, qry]);

  // Memoized check to see if user-selectable roles have been changed.
  const rolesChanged = useMemo(() => {
    const initialRoles = selectedUser?.roles || [];
    const selectableRoleKeys = userSelectableRoles.map((r) => r.key);
    const initialSelectableRoles = initialRoles.filter((r: UserRoles) =>
      selectableRoleKeys.includes(r)
    );
    const currentSelectableRoles = userRoles.filter((r: UserRoles) =>
      selectableRoleKeys.includes(r)
    );

    return (
      initialSelectableRoles.length !== currentSelectableRoles.length ||
      initialSelectableRoles.some((r: UserRoles) => !currentSelectableRoles.includes(r))
    );
  }, [selectedUser, userRoles, userSelectableRoles]);

  useEffect(() => {
    // Initializes userRoles in the Redux store when a user is selected.
    if (selectedUser) {
      reduxStore.dispatch(setUserRoles(selectedUser.roles || []));
    }
  }, [selectedUser, reduxStore.dispatch]);

  if (!selectedUser) {
    return <NoData>There is no selected user</NoData>;
  }

  // Toggles the selection of a role.
  const handleSelectRole = (roleKey: UserRoles) => {
    const newRoles = userRoles.includes(roleKey as UserRoles)
      ? userRoles.filter((r: UserRoles) => r !== roleKey)
      : [...userRoles, roleKey];
    reduxStore.dispatch(setUserRoles(newRoles));
  };

  // Adds all user-selectable roles to the user.
  const handleAddAll = () => {
    const selectableRoleKeys = userSelectableRoles.map((r) => r.key);
    const rolesToAdd = selectableRoleKeys.filter((k) => !userRoles.includes(k));
    reduxStore.dispatch(setUserRoles([...userRoles, ...rolesToAdd]));
  };

  // Removes all user-selectable roles from the user.
  const handleRemoveAll = () => {
    const selectableRoleKeys = userSelectableRoles.map((r) => r.key);
    const rolesToKeep = userRoles.filter((k: UserRoles) => !selectableRoleKeys.includes(k));
    reduxStore.dispatch(setUserRoles(rolesToKeep));
  };

  const handleCancel = () => {
    if (rolesChanged) {
      addToast({
        type: NotificationTypeEnum.WARNING,
        message: `Changes to roles for ${selectedUser.userName} discarded`,
      });
    }
    reduxStore.dispatch(setActiveMode(VisualStateModes.LIST));
  };

  // Renders the user roles list and management UI.
  return (
    <>
      <h4>{selectedUser.userName}</h4>
      <p>
        You can search for a role to edit below, delete this user or
        <Button
          className="btn btn-skinny"
          title="Save or discard changes to roles before editing password"
          disabled={rolesChanged}
          onClick={() => reduxStore.dispatch(setActiveMode(VisualStateModes.PASSWORD))}
        >
          <Icon name="key" /> change the user's password
        </Button>
      </p>
      <SearchInput value={qry} onChange={setQry} placeholder="Search for a role..." />
      <br />
      <div className={displayedRoles.length > 10 ? "waltz-scroll-region-350" : ""}>
        <table className="table table-condensed small table-hover">
          <colgroup>
            <col width="10%" />
            <col width="30%" />
            <col width="20%" />
            <col width="30%" />
            <col width="10%" />
          </colgroup>
          <thead>
            <tr>
              <th>
                <Button className="btn btn-skinny" data-testid="add-all" onClick={handleAddAll}>
                  <Icon name="plus" />
                </Button>
                /
                <Button className="btn btn-skinny" data-testid="remove-all" onClick={handleRemoveAll}>
                  <Icon name="minus" />
                </Button>
              </th>
              <th>Role</th>
              <th>Key</th>
              <th>Description</th>
              <th>Custom Role</th>
            </tr>
          </thead>
          <tbody>
            {displayedRoles.length > 0 ? (
              displayedRoles
                .sort((a, b) => a.name.localeCompare(b.name))
                .map((role) => (
                  <tr key={role.key}>
                    <td>
                      <input
                        type="checkbox"
                        checked={userRoles.includes(role.key)}
                        name={role.key}
                        onChange={() => handleSelectRole(role.key)}
                      />
                    </td>
                    <td>{role.name}</td>
                    <td>{role.key}</td>
                    <td>{role.description || ""}</td>
                    <td>
                      <input type="checkbox" disabled={true} checked={role.isCustom} />
                    </td>
                  </tr>
                ))
            ) : (
              <tr>
                <td colSpan={5}>
                  <NoData>There are no roles to display</NoData>
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {userReadOnlyRoles.length > 0 && (
        <div style={{ paddingTop: "1em" }}>
          <NoData type="info">
            This user has {userReadOnlyRoles.length} roles which are read only and cannot
            be edited through this view.
            <Button
              className="btn btn-skinny"
              onClick={() => setExpandedReadOnly(!expandedReadOnly)}
            >
              {expandedReadOnly ? "Hide" : "Show"} additional roles{" "}
              <Icon name={expandedReadOnly ? "caret-up" : "caret-down"} />
            </Button>
            {expandedReadOnly && (
              <div
                className={userReadOnlyRoles.length > 10 ? "waltz-scroll-region-250" : ""}
              >
                <ul>
                  {userReadOnlyRoles.map((role) => (
                    <li key={role.key}>{role.name}</li>
                  ))}
                </ul>
              </div>
            )}
          </NoData>
        </div>
      )}

      <br />
      <input
        className="form-control"
        maxLength={255}
        placeholder="Add a comment to the user's changelog"
        value={comment}
        onChange={(e) => setComment(e.target.value)}
      />
      <div className="help-block small">
        <Icon name="info-circle" /> Add a comment to the change log for this change.
      </div>

      <span>
        <Button
          className="btn btn-success"
          disabled={!rolesChanged}
          onClick={() => updateUserRolesMutation()}
          data-testid="save-updates"
        >
          Save Updates
        </Button>
        <Button
          className="btn btn-danger"
          onClick={() => reduxStore.dispatch(setActiveMode(VisualStateModes.DELETE))}
        >
          Delete User
        </Button>
        <Button className="btn btn-skinny" data-testid="cancel" onClick={handleCancel}>
          Cancel
        </Button>
      </span>
    </>
  );
};

export default UserRolesList;