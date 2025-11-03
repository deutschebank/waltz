import { EntityKind, EntityReference } from "../types/Entity";

export const BASE_URL = "api";
export const DATA_EXTRACT_BASE_URL = "data-extract";

// Register Api Endpoint Paths Here
export const actorBaseUrl = `${BASE_URL}/actor`;
export const actorPath = {
    findAll: () => actorBaseUrl,
    getById: (id: number) => `${actorBaseUrl}/id/${id}`,
};

export const complexityKindBaseUrl = `${BASE_URL}/complexity-kind`;
export const complexityKindPath = {
    findAll: () => complexityKindBaseUrl,
};

export const costKindBaseUrl = `${BASE_URL}/cost-kind`;
export const costKindPath = {
    findAll: () => costKindBaseUrl,
    findBySubjectKind: (subjectKind: EntityKind) =>
        `${costKindBaseUrl}/subject-kind/${subjectKind}`,
};

export const entityAliasBaseUrl = `${BASE_URL}/entity/alias`;
export const entityAliasPath = {
    entityReference: (ref: EntityReference) =>
        `${entityAliasBaseUrl}/${ref.kind}/${ref.id}`,
};

export const entitySearchBaseUrl = `${BASE_URL}/entity-search`;
export const entitySearchPath = {
    search: () => entitySearchBaseUrl,
};

export const personBaseUrl = `${BASE_URL}/person`;
export const personPath = {
    getSelf: () => `${personBaseUrl}/self`,
};

export const staticPanelBaseUrl = `${BASE_URL}/static-panel`;
export const staticPanelPath = {
    load: () => staticPanelBaseUrl,
    save: () => staticPanelBaseUrl,
    findByGroupKey: (groupKey: string) => `${staticPanelBaseUrl}/group?group=${groupKey}`
}

// Register Data Extract Endpoint Paths Here
export const dataExtractPath = {
    extract: () => DATA_EXTRACT_BASE_URL,
    allActors: "actor/all",
};

export const allocationSchemeBaseUrl = `${BASE_URL}/allocation-scheme`;
export const allocationSchemePath: {
    findAll: () => string;} = {
    findAll: () => `${allocationSchemeBaseUrl}/all`,
};
