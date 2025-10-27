import {fetchJSON} from "./api";
import {EntityLifecycleStatus, EntityReference} from "../types/Entity";
import {EntityKind} from "../types/Entity";
import {entitySearchPath} from "../constants/path";

const search = (query: string,
                entityKinds: EntityKind[] = [],
                limit: number = 5,
                entityLifecycleStatuses : EntityLifecycleStatus[] = ["ACTIVE", "PENDING"]) => ({
    queryKey: ['entitySearch', query, entityKinds, entityLifecycleStatuses, limit],
    queryFn: async (): Promise<EntityReference[]> => {
        let options = {
            entityKinds: entityKinds,
            limit: limit,
            entityLifecycleStatuses: entityLifecycleStatuses,
            searchQuery: query
        };

        return await fetchJSON(entitySearchPath.search(), "POST", options);
    }
});



export default {
    search
}