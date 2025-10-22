import { fetchJSONList } from "./api";
import PATH from "../constants/path";
import { ComplexityKind } from "../types/Grid";

// Fetch complexity kinds
export const complexityQuery = () => ({
    queryKey: ["complexity-kind"],
    queryFn: async () => {
        const response: ComplexityKind[] = await fetchJSONList(
            `${PATH.complexityKindUrl}`
        );
        return response;
    },
});
