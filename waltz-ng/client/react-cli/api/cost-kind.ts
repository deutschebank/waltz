import {fetchJSON} from "./api";
import PATH from "../constants/path";
import {CostKind} from "../components/common/Picker/CostKindPicker";

// Fetch all cost kinds
export const findAll = () => ({
    queryKey: ["cost-kind"],
    queryFn: async () => {
        return await fetchJSON<CostKind[]>(`${PATH.costKind}`);
    },
});

// Fetch by subject kind
export const findBySubjectKind = (subjectKind: string) => ({
    queryKey: ["cost-subjectKind", subjectKind],
    queryFn: async () => {
        return await fetchJSON<CostKind[]>(
            `${PATH.costSubjectKind}/${subjectKind}`
        );
    },
    enabled: !!subjectKind,
});
