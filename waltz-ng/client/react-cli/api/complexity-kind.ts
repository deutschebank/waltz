import {fetchJSON} from "./api";
import PATH from "../constants/path";
import {ComplexityKind} from "../types/Grid";

// Fetch complexity kinds
export const complexityQuery = () => ({
    queryKey: ["complexity-kind"],
    queryFn: async () => {
        return await fetchJSON<ComplexityKind[]>(
            `${PATH.complexityKindUrl}`
        );
    },
});
