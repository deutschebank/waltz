import { Roles } from "../enums/User";

export interface Role {
  key: Roles;
  name: string;
  description: string;
  userSelectable?: boolean;
  isCustom?: boolean;
}

export interface UserInfo {
  userId?: string;
  name?: string;
  userName: string;
  roles: Roles[];
  comment?: string;
}

export interface CreateUser {
  userName: string;
  password: string;
}

// Defines the structure for a preview row.
export interface PreviewRow {
  givenUser: string;
  givenRole: string;
  givenComment: string;
  resolvedUser: string | null;
  resolvedRole: string | null;
  resolvedComment: string | null;
}

export type UserBulkResponse<T> = {
  data: T; // Array of user objects
};

// Defines the shape of the user management state.
export interface UserManagementState {
  // The currently selected user, or null if none is selected.
  selectedUser: UserInfo | null;
  // The active mode of the user management panel (e.g., LIST, DETAIL, ADD).
  activeMode: string;
  // The roles associated with the selected user.
  userRoles: Roles[];
  // The search query for filtering users or roles.
  searchQry: string;
}