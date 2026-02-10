import useDefaultRatingSchemeFilter from "../../common/filters/use-default-rating-scheme-filter";
import {RatingSchemeItemType} from "../types/Rating";

export function useDefaultRatingScheme(ratingCode: string, schemeName: string): RatingSchemeItemType {
    const useDefaultRatingSchemeFilterFn = useDefaultRatingSchemeFilter();
    return useDefaultRatingSchemeFilterFn(ratingCode, schemeName);
}