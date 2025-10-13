export const enum EntityKind {
    APPLICATION,
    ACTOR,
    DATABASE,
    END_USER_APPLICATION,
    PERSON,
    SERVER
}

export const enum EntityLifecycleStatus {
    ACTIVE,
    PENDING,
    REMOVED,
    UNKNOWN// only for UI edge cases
}