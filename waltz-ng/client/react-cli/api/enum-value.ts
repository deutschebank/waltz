import {fetchJSON} from "./api";
import {enumValuePath} from "../constants/path";
import {EnumValueType} from "../types/EnumValue";

const load = () => ({
  queryKey: ["enum", "load"],
  queryFn: async (): Promise<EnumValueType[]> => {
    return await fetchJSON(enumValuePath.findAll());
  },
});

export const enumValueApi = {
  load,
};
