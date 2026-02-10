import {EntityKind} from "../enums/Entity";

export type OrgUnitType = {
    id: number;
    name: string;
    description: string;
    externalId: string;
    kind: EntityKind.ORG_UNIT;
}