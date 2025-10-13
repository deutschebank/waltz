import {EntityKind} from "../enums/Entity";

export type Actor = {
    id: 1,
    name: string,
    description: string,
    lastUpdatedAt: string,
    lastUpdatedBy: string,
    provenance: string,
    externalId: string,
    kind: EntityKind.ACTOR,
    isExternal: boolean
}