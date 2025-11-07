import {MeasurableCategoryType, MeasurableHierarchyType, MeasurableType} from "./Measurable";
import {EntityReference} from "./Entity";
import {LastUpdatedProvider, ProvenanceProvider} from "./Providers";
import {RatingSchemeItemType} from "./Rating";

export interface MeasurableRatingType extends ProvenanceProvider, LastUpdatedProvider{
    id: number,
    description: string,
    entityReference: EntityReference,
    measurableId: number,
    rating: string,
    ratingId: number,
    isReadOnly: boolean,
    isPrimary: boolean
}

export type MeasurableRatingViewType = {
    measurableCategories: MeasurableCategoryType[],
    measurableHierarchy: MeasurableHierarchyType,
    measurableRatings: MeasurableRatingType[],
    measurables: MeasurableType[],
    ratingSchemeItems: RatingSchemeItemType[],
}