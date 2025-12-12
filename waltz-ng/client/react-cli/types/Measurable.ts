import {EntityReference} from "./Entity";
import {EntityKind} from "../enums/Entity";
import {LastUpdatedProvider, ProvenanceProvider} from "./Providers";

export interface MeasurableType extends EntityReference, ProvenanceProvider, LastUpdatedProvider {
    parentId: number,
    externalParentId: string,
    position: number,
    categoryId: number,
    concrete: boolean,
    organisationalUnitId?: number,
    kind: EntityKind.MEASURABLE
}

export interface MeasurableCategoryType extends LastUpdatedProvider{
    id: number,
    name: string,
    description: string,
    externalId: string,
    icon: string,
    position: number,
    kind: EntityKind.MEASURABLE_CATEGORY,
    editable: boolean,
    ratingEditorRole: string,
    ratingSchemeId: number,
    constrainingAssessmentDefinitionId?: number,
    allowPrimaryRatings: boolean,
    isDeprecated: boolean
}

export interface MeasurableHierarchyType {
    measurableId: number;
    parents: MeasurableHierarchyAlignmentType[];
    maxDepth: number;
}

export interface MeasurableHierarchyAlignmentType {
    parentReference: EntityReference;
    level: number;
}