import {EntityReference} from "../types/Entity";

export function mkRef<T> (entity: T): EntityReference {
    return {
        id: entity["id"] ?? -1,
        kind: entity["kind"] ?? "",
        name: entity["name"] ?? "",
        externalId: entity["externalId"] ?? "",
        description: entity["description"] ?? "",
        entityLifecycleStatus: entity["entityLifecycleStatus"] ?? "UNKNOWN"
    };
}