import {EntityKind, EntityLifecycleStatus} from "./Entity";

export type IsRemovedProvider = {
    isRemoved: boolean
}

export type HostnameProvider = {
    hostname: string
}

export type DatabaseNameProvider = {
    databaseName: string
}

export interface ReferencableProvider {
    id?: number;
    kind?: EntityKind;
    name?: string;
    externalId?: string;
    description?: string;
    entityLifecycleStatus?: EntityLifecycleStatus;
}

export type IsReadOnlyProvider = {
    isReadOnly: boolean
}

export type ProvenanceProvider = {
    provenance: string
}

export type LastUpdatedProvider = {
    lastUpdatedAt: string;
    lastUpdatedBy: string;
}