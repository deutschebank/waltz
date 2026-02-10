import {measurableRatingPath} from "../constants/path";

import {fetchJSON} from "./api";
import {MeasurableRatingViewType} from "../types/MeasurableRating";
import {SelectionOptions} from "../types/SelectionOptions";

const getPrimaryRatingsViewBySelector = (selectionOptions: SelectionOptions) => ({
    queryKey: ["measurableRating", "getPrimaryRatingsViewBySelector", selectionOptions],
    queryFn: async () => {
        return await fetchJSON<MeasurableRatingViewType>(measurableRatingPath.getPrimaryRatingsViewBySelector(), "POST", selectionOptions);
    }
});

export const measurableRatingApi = {
    getPrimaryRatingsViewBySelector
};
