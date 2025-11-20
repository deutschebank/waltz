import {EntityReference} from "../types/Entity";
import {ReferencableProvider} from "../types/Providers";

export function mkRef<T extends ReferencableProvider> (entity: T): EntityReference {
    return {
        id: entity.id ?? -1,
        kind: entity.kind ?? "UNKNOWN",
        name: entity.name ?? "",
        externalId: entity.externalId ?? "",
        description: entity.description ?? "",
        entityLifecycleStatus: entity.entityLifecycleStatus ?? "UNKNOWN"
    };
}