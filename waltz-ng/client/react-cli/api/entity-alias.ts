import { fetchJSON } from "./api";
import { entityAliasPath } from "../constants/path";
import { EntityReference } from "../types/Entity";

// Fetch aliases
export const getEntityReference = (ref: EntityReference) => ({
    queryKey: ["entity-alias", ref],
    queryFn: async (): Promise<string[]> => {
        return await fetchJSON(entityAliasPath.entityReference(ref));
    },
    enabled: !!ref,
});

// Update aliases
export const updateEntityReference = (
    ref: EntityReference,
    aliases: string[] = []
) => ({
    queryFn: async () => {
        return await fetchJSON(
            entityAliasPath.entityReference(ref),
            "POST",
            aliases
        );
    },
});
