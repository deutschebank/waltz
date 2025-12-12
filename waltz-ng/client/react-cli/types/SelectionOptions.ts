import {EntityKind, EntityLifecycleStatus, EntityReference} from "./Entity";
import {HierarchyQueryScopeType} from "./HierarchyQueryScope";

export type SelectionOptions = {
    entityReference: EntityReference,
    scope: HierarchyQueryScopeType,
    entityLifecycleStatuses?: EntityLifecycleStatus[],
    filters?: any, // what is the type for filters ?
    joiningEntityKind?: EntityKind
}