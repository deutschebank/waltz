import {assessmentRatingPath} from "../constants/path";
import {fetchJSON} from "./api";
import {AssessmentRating} from "../types/Assessment";
import {EntityReference} from "../types/Entity";

const findForEntityReference = (ref: EntityReference) => ({
    queryKey: ["assessment-rating", "findForEntityReference", ref],
    queryFn: async () => {
        return await fetchJSON<AssessmentRating[]>(assessmentRatingPath.findForEntityReference(ref));
    }
})

export const assessmentRatingApi = {
    findForEntityReference
}
