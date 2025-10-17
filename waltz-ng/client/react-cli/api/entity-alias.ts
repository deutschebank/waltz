import { fetchJSONList } from "./api";
import { RestMethod } from "../types/Http";
import PATH from "../constants/path";

const alias = (
    url: string,
    method: RestMethod = "GET",
    data: any,
    config: any
) => ({
    queryKey: ["entity-alias", url, method, data, config],
    queryFn: async () => {
        return await fetchJSONList(
            `${PATH.entityAliasUrl}/${url}`,
            method,
            data
        );
    },
});

export default {
    alias,
};
