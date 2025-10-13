export const enum EntityKind {
    PERSON,
    APPLICATION,
    ACTOR,
    END_USER_APPLICATION,
    SERVER,
    DATABASE
}

export const enum EntityLifecycleStatus {
    ACTIVE,
    PENDING,
    REMOVED,
    UNKNOWN
}