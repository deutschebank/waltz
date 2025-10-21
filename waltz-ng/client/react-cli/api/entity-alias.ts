import { fetchJSONList } from "./api";
import PATH from "../constants/path";

type ref = {
    kind: string;
    id: number;
};

// Fetch aliases
export const aliasQuery = (ref: ref) => ({
    queryKey: ["entity-alias", ref],
    queryFn: async () => {
        const response: string[] = await fetchJSONList(
            `${PATH.entityAliasUrl}/${ref.kind}/${ref.id}`
        );
        return response;
    },
    enabled: !!ref,
});

// Update aliases
export const updateAliases = (ref: ref, aliases: string[] = []) => ({
    queryFn: async () => {
        return await fetchJSONList(
            `${PATH.entityAliasUrl}/${ref.kind}/${ref.id}`,
            "POST",
            aliases
        );
    },
});
