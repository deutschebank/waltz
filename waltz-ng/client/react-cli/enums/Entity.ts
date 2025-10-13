export const enum EntityKind {
    APPLICATION = "APPLICATION",
    ACTOR = "ACTOR",
    DATABASE = "DATABASE",
    END_USER_APPLICATION = "END_USER_APPLICATION",
    PERSON = "PERSON",
    SERVER = "SERVER",
    UNKNOWN = "UNKNOWN" // only for UI edge cases
}

export const enum EntityLifecycleStatus {
    ACTIVE = "ACTIVE",
    PENDING = "PENDING",
    REMOVED = "REMOVED",
    UNKNOWN = "UNKNOWN" // only for UI edge cases
}