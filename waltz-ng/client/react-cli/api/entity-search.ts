import {baseApiUrl, fetchJSON} from "./api";
import {EntityReference} from "../types/Entity";

const entitySearchUrl = `${baseApiUrl}/entity-search`;

const search = (query: string,
                entityKinds: string[] = [],
                limit: number = 5,
                entityLifecycleStatuses : string[] = ["ACTIVE", "PENDING"]) => ({
    queryKey: ['entitySearch', query, entityKinds, entityLifecycleStatuses, limit],
    queryFn: async (): Promise<EntityReference[]> => {
        let options = {
            entityKinds: entityKinds,
            limit: limit,
            entityLifecycleStatuses: entityLifecycleStatuses,
            searchQuery: query
        };

        return await fetchJSON(entitySearchUrl, "POST", options);
    }
});



export default {
    search
}