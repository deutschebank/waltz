import {fetchJSON} from "./api";
import {complexityKindPath} from "../constants/path";
import {ComplexityKind} from "../types/Grid";

// Fetch complexity kinds
export const findAll = () => ({
    queryKey: ["complexity-kind"],
    queryFn: async (): Promise<ComplexityKind[]> => {
        return await fetchJSON(
            complexityKindPath.findAll()
        );
    },
});
