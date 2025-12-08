import {fetchJSON} from "./api";
import {enumValueBaseUrl} from "../constants/path";

const load = () => ({
  queryKey: ["enum", "load"],
  queryFn: async (): Promise<any> => {
    return await fetchJSON(enumValueBaseUrl);
  }
});

export const enumLoadApi = {
  load
};
