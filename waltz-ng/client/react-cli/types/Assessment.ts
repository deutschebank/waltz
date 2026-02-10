import {EntityKind, EntityReference} from "./Entity";
import {IsReadOnlyProvider, LastUpdatedProvider, ProvenanceProvider} from "./Providers";
import {AssessmentCardinalityEnum, AssessmentVisibilityEnum} from "../enums/Assessment";


export type AssessmentVisibilityType = keyof typeof AssessmentVisibilityEnum;
export type AssessmentCardinalityType = keyof typeof AssessmentCardinalityEnum;

export interface AssessmentRating extends LastUpdatedProvider, ProvenanceProvider, IsReadOnlyProvider{
    entityReference: EntityReference;
    assessmentDefinitionId: number;
    ratingId: number;
    description?: string;
    id: number;
    comment?: string;

}

export interface AssessmentDefinition extends LastUpdatedProvider, IsReadOnlyProvider, ProvenanceProvider {
    id: number;
    name: string;
    externalId?: string;
    ratingSchemeId: number;
    entityKind: EntityKind;
    description: string;
    permittedRole?: string;
    visibility: AssessmentVisibilityType;
    definitionGroup: string;
    qualifierReference?: EntityReference;
    cardinality: AssessmentCardinalityType
}