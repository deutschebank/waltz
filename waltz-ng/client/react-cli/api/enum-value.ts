import {fetchJSON} from "./api";
import {enumValuePath} from "../constants/path";
import { ENUMValueType } from "../types/EnumValue";

const load = () => ({
  queryKey: ["enum", "load"],
  queryFn: async (): Promise<ENUMValueType[]> => {
    return await fetchJSON(enumValuePath.findAll());
  },
});

export const enumValueApi = {
  load,
};
