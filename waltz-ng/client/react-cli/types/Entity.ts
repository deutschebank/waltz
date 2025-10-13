import { EntityKind as EntityKindEnum,
    EntityLifecycleStatus as EntityLifecycleStatusEnum } from "../enums/Entity";

export type EntityKind = keyof typeof EntityKindEnum;
export type EntityLifecycleStatus = keyof typeof EntityLifecycleStatusEnum;

export type EntityReference = {
    description: string
    kind: EntityKind
    id: number
    name: string
    externalId: string
    entityLifecycleStatus: EntityLifecycleStatus
}

export type EntityDefinitionValue = {
    key: string
    name: string
    icon: string
    description: string
    position: number
}

export interface EntityDefinition {
    [key: string]: EntityDefinitionValue
}