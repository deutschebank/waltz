import {fetchJSON} from "./api";
import {enumValueBaseUrl} from "../constants/path";

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
    return await fetchJSON(enumValueBaseUrl);
  },
});

export const enumValueApi = {
  load,
};
