import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { IUser } from "../react-cli/types/User";
import { Modes, Roles } from "../react-cli/enums/User";

// Defines the shape of the user management state.
interface UserManagementState {
  // The currently selected user, or null if none is selected.
  selectedUser: IUser | null;
  // The active mode of the user management panel (e.g., LIST, DETAIL, ADD).
  activeMode: string;
  // The roles associated with the selected user.
  userRoles: Roles[];
  // The search query for filtering users or roles.
  searchQry: string;
}

// Defines the initial state for the user management slice.
const initialState: UserManagementState = {
  selectedUser: null,
  activeMode: Modes.LIST,
  userRoles: [],
  searchQry: "",
};

// Creates a Redux slice for user management with its initial state and reducers.
export const userManagementSlice = createSlice({
  name: "userManagement",
  initialState,
  reducers: {
    // Sets the currently selected user.
    setSelectedUser: (state, action: PayloadAction<IUser | null>) => {
      state.selectedUser = action.payload;
    },
    // Sets the active mode of the user management panel.
    setActiveMode: (state, action: PayloadAction<string>) => {
      state.activeMode = action.payload;
    },
    // Sets the roles for the selected user.
    setUserRoles: (state, action: PayloadAction<Roles[]>) => {
      state.userRoles = action.payload;
    },
    // Sets the search query string.
    setSearchQry: (state, action: PayloadAction<string>) => {
      state.searchQry = action.payload;
    },
  },
});

// Exports the action creators for the user management slice.
export const { setSelectedUser, setActiveMode, setUserRoles, setSearchQry } =
  userManagementSlice.actions;

// Exports the reducer function for the user management slice.
export default userManagementSlice.reducer;