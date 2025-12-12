import {EntityKind} from "../enums/Entity";
import {IsReadOnlyProvider, IsRemovedProvider, ProvenanceProvider} from "./Providers";
import {EntityReference} from "./Entity";
import {AppGroupKindEnum} from "../enums/AppGroup";

export interface AppGroupType extends IsRemovedProvider {
    id: number;
    name: string;
    description: string;
    externalId: string;
    appGroupKind: AppGroupKindType;
    kind: EntityKind.APP_GROUP;
    isFavouriteGroup: boolean;
}

export type AppGroupKindType = keyof typeof AppGroupKindEnum;

export type AppGroupEntry = EntityReference & IsReadOnlyProvider & ProvenanceProvider;