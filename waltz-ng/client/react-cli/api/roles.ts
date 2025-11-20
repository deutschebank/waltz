import { fetchJSON } from "./api";
import { rolePath } from "../constants/path";
import { Role } from "../types/User";

const findAll = () => ({
  queryKey: ["role", "findAll"],
  queryFn: async (): Promise<Role[]> => {
    return await fetchJSON(rolePath.findAll());
  },
});

const getViewById = (id: number) => ({
  queryKey: ["role", "getViewById", id],
  queryFn: async (): Promise<string> => {
    return await fetchJSON(rolePath.getViewById(id));
  },
  enabled: !!id,
});

export const roleApi = {
  findAll,
  getViewById,
}