export interface RatingSchemeType {
    id: number;
    name: string;
    description: string;
    externalId: string;
    ratings: RatingSchemeItemType[];
}

export interface RatingSchemeItemType {
    id: number;
    schemeId: number;
    name: string;
    description: string;
    code: string;
    color: string;
    position: number;
    userSelectable: boolean;
    externalId: string;
    ratingGroup: string;
    requiresComment: boolean;
}