import {fetchJSON} from "./api";
import {costKindPath} from "../constants/path";
import {CostKind} from "../components/common/Picker/CostKindPicker";
import {EntityKind} from "../types/Entity";

// Fetch all cost kinds
export const findAll = () => ({
    queryKey: ["cost-kind"],
    queryFn: async (): Promise<CostKind[]> => {
        return await fetchJSON(costKindPath.findAll());
    },
});

// Fetch by subject kind
export const findBySubjectKind = (subjectKind: EntityKind) => ({
    queryKey: ["cost-subjectKind", subjectKind],
    queryFn: async (): Promise<CostKind[]> => {
        return await fetchJSON(costKindPath.findBySubjectKind(subjectKind));
    },
    enabled: !!subjectKind,
});
