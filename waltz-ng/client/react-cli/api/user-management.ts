import { fetchJSON, execute } from "./api";
import { userPath } from "../constants/path";
import { Person } from "../types/Person";
import {
  ICreateUser,
  IUser,
  UserBulkResponse,
  PreviewRow,
} from "../types/User";
import { Roles } from "../enums/User";

const findAll = () => ({
  queryKey: ["user", "findAll"],
  queryFn: async (): Promise<IUser[]> => {
    return await fetchJSON(userPath.findAll());
  },
});

const whoami = () => ({
  queryKey: ["user", "whoami"],
  queryFn: async (): Promise<Person> => {
    return await fetchJSON(userPath.whoami());
  },
});

const getByUserId = (userId: string) => ({
  queryKey: ["user", "getByUserId", userId],
  queryFn: async (): Promise<IUser> => {
    return await fetchJSON(userPath.getByUserId(userId));
  },
  enabled: !!userId,
});

const updateRoles = (userName: string, roles: Roles[], comment: string) => ({
  mutationKey: ["user", "updateRoles", userName],
  mutationFn: async (): Promise<number> => {
    return await execute(userPath.updateRoles(userName), "POST", {
      roles,
      comment,
    });
  },
});

const register = (newUser: ICreateUser) => ({
  mutationKey: ["user", "register"],
  mutationFn: async (): Promise<boolean> => {
    return await execute(userPath.register(), "POST", newUser);
  },
});

const resetPassword = (
  userName: string,
  newPassword: any,
  currentPassword: any
) => ({
  mutationKey: ["user", "resetPassword", userName],
  mutationFn: async (): Promise<boolean> => {
    return await execute(userPath.resetPassword(), "POST", {
      userName,
      newPassword,
      currentPassword,
    });
  },
});

const bulkUploadPreview = (mode: string, rows: any = []) => ({
  mutationKey: ["user", "bulkUploadPreview", mode],
  mutationFn: async (): Promise<UserBulkResponse<PreviewRow[]>> => {
    return await execute(userPath.bulkUploadPreview(mode), "POST", rows);
  },
});

const bulkUpload = (mode: string, rows: any = []) => ({
  mutationKey: ["user", "bulkUpload", mode],
  mutationFn: async (): Promise<UserBulkResponse<number>> => {
    return await execute(userPath.bulkUpload(mode), "POST", rows);
  },
});

const deleteUser = (username: string) => ({
  mutationKey: ["user", "deleteUser", username],
  mutationFn: async (): Promise<boolean> => {
    return await execute(userPath.deleteUser(username), "DELETE");
  },
});

export const userManagementApi = {
  findAll,
  whoami,
  getByUserId,
  updateRoles,
  register,
  resetPassword,
  bulkUploadPreview,
  bulkUpload,
  deleteUser,
};