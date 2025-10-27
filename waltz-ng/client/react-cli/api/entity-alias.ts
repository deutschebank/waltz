import {fetchJSON} from "./api";
import PATH from "../constants/path";

type ref = {
    kind: string;
    id: number;
};

// Fetch aliases
export const aliasQuery = (ref: ref) => ({
    queryKey: ["entity-alias", ref],
    queryFn: async (): Promise<string[]> => {
        return await fetchJSON(
            `${PATH.entityAliasUrl}/${ref.kind}/${ref.id}`
        );
    },
    enabled: !!ref,
});

// Update aliases
export const updateAliases = (ref: ref, aliases: string[] = []) => ({
    queryFn: async () => {
        return await fetchJSON(
            `${PATH.entityAliasUrl}/${ref.kind}/${ref.id}`,
            "POST",
            aliases
        );
    },
});
