import { fetchJSONList } from "./api";
import PATH from "../constants/path";
import { CostKind } from "../components/common/CostKindPicker";

// Fetch all cost kinds
export const findAll = () => ({
    queryKey: ["cost-kind"],
    queryFn: async () => {
        const response: CostKind[] = await fetchJSONList(`${PATH.costKind}`);
        return response;
    },
});

// Fetch by subject kind
export const findBySubjectKind = (subjectKind: string) => ({
    queryKey: ["cost-subjectKind", subjectKind],
    queryFn: async () => {
        const response: CostKind[] = await fetchJSONList(
            `${PATH.costSubjectKind}/${subjectKind}`
        );
        return response;
    },
    enabled: !!subjectKind,
});
