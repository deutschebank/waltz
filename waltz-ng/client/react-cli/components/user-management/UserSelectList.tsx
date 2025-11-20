import React, { useState, useMemo } from "react";
import SearchInput from "../common/SearchInput";
import NoData from "../common/no-data/NoData";
import Icon from "../common/Icon";
import { userManagementApi } from "../../api/user-management";
import { useQuery } from "@tanstack/react-query";
import {
  setActiveMode,
  setSelectedUser,
  setUserRoles,
} from "../../../redux-slices/user-management-slice";
import Loader from "../common/loader/Loader";
import reduxStore from "../../../redux-store";
import { IUser } from "../../types/User";
import { Modes } from "../../enums/User";
import Button from "../common/button/Button";

/**
 * UserSelectList displays a searchable list of all users.
 */
const UserSelectList: React.FC = () => {
  // State for the search query to filter the user list.
  const [searchQry, setSearchQry] = useState<string>("");

  // Fetches all users from the server using React Query.
  const { data: fetchedUsers = [], isLoading } = useQuery(userManagementApi.findAll());

  // Memoizes the sorted list of users.
  const users = useMemo(() => {
    return [...fetchedUsers].sort((a, b) => a.userName.localeCompare(b.userName));
  }, [fetchedUsers]);

  // Filters the users based on the search query.
  const displayedUsers =
    searchQry === ""
      ? users
      : users.filter((user) => {
          return user.userName?.toLowerCase().includes(searchQry.toLowerCase());
        });

  // Handles selecting a user from the list, updating the Redux store.
  const selectUser = (user: IUser) => {
    reduxStore.dispatch(setSelectedUser(user));
    reduxStore.dispatch(setUserRoles(user.roles));
    reduxStore.dispatch(setActiveMode(Modes.DETAIL));
  };

  // Renders the user list, search input, and loading state.
  return (
    <>
      <p>
        Use the search below to select a user and edit their roles or
        <button
          className="btn btn-skinny"
          data-testid="add-user-btn"
          onClick={() => reduxStore.dispatch(setActiveMode(Modes.ADD))}
        >
          <Icon name="plus" /> add a new user
        </button>
        .
      </p>
      <SearchInput
        value={searchQry}
        onChange={setSearchQry}
        placeholder="Search for a user..."
      />
      <br />
      {displayedUsers.length > 100 ? (
        <NoData type="info">
          <Icon name="exclamation-triangle" /> There are too many results to show, please
          use the search to filter the list
        </NoData>
      ) : (
        <div
          className="waltz-scroll-region-350"
          style={{ overflowY: "auto", maxHeight: "350px" }}
        >
          <table className="table table-condensed small table-hover">
            {isLoading ? (
              <tbody>
                <tr>
                  <td>
                    <Loader />
                  </td>
                </tr>
              </tbody>
            ) : (
              <>
                <thead>
                  <tr>
                    <th>Username</th>
                  </tr>
                </thead>
                <tbody>
                  {displayedUsers.map((user) => (
                    <tr
                      className="clickable"
                      key={user.userName}
                      onClick={() => selectUser(user)}
                    >
                      <td>
                        <Button
                          className="btn btn-skinny"
                          onClick={() => selectUser(user)}
                        >
                          {user.userName}
                        </Button>
                      </td>
                    </tr>
                  ))}
                  {displayedUsers.length === 0 && (
                    <tr>
                      <td>
                        <NoData>There are no users to display</NoData>
                      </td>
                    </tr>
                  )}
                </tbody>
              </>
            )}
          </table>
        </div>
      )}
    </>
  );
};

export default UserSelectList;