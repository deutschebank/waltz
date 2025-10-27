import {EntityKind} from "../types/Entity";

export const BASE_URL = "api";
export const DATA_EXTRACT_BASE_URL = "data-extract";

const PATHS = {
    entityAliasUrl: `${BASE_URL}/entity/alias`
};

// Register Api Endpoint Paths Here
export const actorBaseUrl = `${BASE_URL}/actor`;
export const actorPath = {
    findAll: () => actorBaseUrl,
    getById: (id: number) => `${actorBaseUrl}/id/${id}`
}

export const complexityKindBaseUrl = `${BASE_URL}/complexity-kind`;
export const complexityKindPath = {
    findAll: () => complexityKindBaseUrl,
}

export const costKindBaseUrl = `${BASE_URL}/cost-kind1`;
export const costKindPath = {
    findAll: () => costKindBaseUrl,
    findBySubjectKind: (subjectKind: EntityKind) => `${costKindBaseUrl}/subject-kind/${subjectKind}`
}

export const entityAliasBaseUrl = `${BASE_URL}/entity/alias`;
export const entityAliasPath = {}

export const entitySearchBaseUrl = `${BASE_URL}/entity-search`;
export const entitySearchPath = {
    search: () => entitySearchBaseUrl,
}

export const personBaseUrl = `${BASE_URL}/person`;
export const personPath = {
    getSelf: () => `${personBaseUrl}/self`
}

// Register Data Extract Endpoint Paths Here
export const dataExtractPath = {
    extract: () => DATA_EXTRACT_BASE_URL,
    allActors: "actor/all"
}
export default PATHS;
