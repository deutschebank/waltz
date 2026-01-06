import {fetchJSON} from "./api";
import {enumValuePath} from "../constants/path";

type ENUMValueType = {
  key: string;
  name: string;
  icon: string;
  description: string;
  position: number;
  type: string;
  iconColor: string;
};

const load = () => ({
  queryKey: ["enum", "load"],
  queryFn: async (): Promise<ENUMValueType[]> => {
    return await fetchJSON(enumValuePath.findAll());
  },
});

export const enumValueApi = {
  load,
};
