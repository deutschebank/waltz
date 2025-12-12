export enum LifecyclePhaseEnum {
    DEVELOPMENT = "DEVELOPMENT",
    RETIRED = "RETIRED",
    PRODUCTION = "PRODUCTION",
    CONCEPTUAL = "CONCEPTUAL"
}

export enum ApplicationBusinessCriticalityEnum {
    UNKNOWN = "UNKNOWN",
    NONE = "NONE",
    LOW = "LOW",
    MEDIUM = "MEDIUM",
    HIGH = "HIGH",
    VERY_HIGH = "VERY_HIGH"
}

/**
 * @enum R Disinvest
 * @enum A Maintain
 * @enum G Invest
 * @enum Z Unknown
 */
export enum ApplicationOverallRatingEnum {
    R = 'R',
    A = 'A',
    G = 'G',
    Z = 'Z'
}