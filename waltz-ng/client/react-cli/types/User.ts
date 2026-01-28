import {Roles} from "../enums/User";
export interface Role {
  id: number;
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

export interface CreateUserType {
  userName: string;
  password: string;
}

export interface PreviewRow {
  givenUser: string;
  givenRole: string;
  givenComment: string;
  resolvedUser: string | null;
  resolvedRole: string | null;
  resolvedComment: string | null;
}

export type UserBulkUploadPreviewResponse = {
  data: PreviewRow[];
};

export type UserBulkUploadResponse = {
  data: number;
};

export interface UserManagementState {
  selectedUser: UserInfo | null;
  activeMode: string;
  userRoles: Roles[];
  searchQry: string;
}
