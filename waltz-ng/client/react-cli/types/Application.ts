import {EntityKind} from "../enums/Entity";
import {IsRemovedProvider, ProvenanceProvider} from "./Providers";
import {EntityReference} from "./Entity";
import {
    ApplicationBusinessCriticalityEnum,
    ApplicationOverallRatingEnum,
    LifecyclePhaseEnum
} from "../enums/Application";

export type LifecyclePhaseType = keyof typeof LifecyclePhaseEnum;

export type ApplicationBusinessCriticalityType = keyof typeof ApplicationBusinessCriticalityEnum;

/**
 * @enum R Disinvest
 * @enum A Maintain
 * @enum G Invest
 * @enum Z Unknown
 *
 * this definition has been obtained from the column's definition
 */
export type ApplicationOverallRatingType = keyof typeof ApplicationOverallRatingEnum;

export interface ApplicationType extends EntityReference, IsRemovedProvider, ProvenanceProvider {
    organisationalUnitId: number;
    assetCode: string;
    parentAssetCode?: string;
    applicationKind?: string;
    lifecyclePhase: LifecyclePhaseType;
    overallRating: ApplicationOverallRatingType
    plannedRetirementDate?: string;
    actualRetirementDate?: string;
    commissionDate?: string;
    kind: EntityKind.APPLICATION;
    businessCriticality?: ApplicationBusinessCriticalityType;
    createdAt: string;
    updatedAt: string;
}
