import { Roles } from "../enums/User";

export interface Role {
  key: Roles;
  name: string;
  description: string;
  userSelectable?: boolean;
  isCustom?: boolean;
}

export interface IUser {
  userId?: string;
  name?: string;
  userName: string;
  roles: Roles[];
  comment?: string;
}

export interface ICreateUser {
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