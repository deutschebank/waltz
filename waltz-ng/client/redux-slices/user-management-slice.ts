import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {UserInfo, UserManagementState} from "../react-cli/types/User";
import {UserRoles} from "../react-cli/enums/User";
import {VisualStateModes} from "../react-cli/enums/VisualState";

const initialState: UserManagementState = {
  selectedUser: null,
  activeMode: VisualStateModes.LIST,
  userRoles: [],
  searchQry: "",
};

export const userManagementSlice = createSlice({
  name: "userManagement",
  initialState,
  reducers: {
    // Sets the currently selected user.
    setSelectedUser: (state, action: PayloadAction<UserInfo | null>) => {
      state.selectedUser = action.payload;
    },
    // Sets the active mode of the user management panel.
    setActiveMode: (state, action: PayloadAction<string>) => {
      state.activeMode = action.payload;
    },
    // Sets the roles for the selected user.
    setUserRoles: (state, action: PayloadAction<UserRoles[]>) => {
      state.userRoles = action.payload;
    },
    // Sets the search query string.
    setSearchQry: (state, action: PayloadAction<string>) => {
      state.searchQry = action.payload;
    },
  },
});

export const {setSelectedUser, setActiveMode, setUserRoles, setSearchQry} =
  userManagementSlice.actions;

export default userManagementSlice.reducer;
