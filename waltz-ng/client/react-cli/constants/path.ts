import {EntityKind, EntityReference} from "../types/Entity";

export const BASE_URL = "api";
export const DATA_EXTRACT_BASE_URL = "data-extract";

// Register Api Endpoint Paths Here
export const actorBaseUrl = `${BASE_URL}/actor`;
export const actorPath = {
  findAll: () => actorBaseUrl,
  getById: (id: number) => `${actorBaseUrl}/id/${id}`,
};

export const allocationSchemeBaseUrl = `${BASE_URL}/allocation-scheme`;
export const allocationSchemePath = {
  findAll: () => `${allocationSchemeBaseUrl}/all`,
};

export const bookmarkPath = {
  load: (ref: EntityReference) => `${BASE_URL}/bookmarks/${ref.kind}/${ref.id}`,
  save: () => `${BASE_URL}/bookmarks`,
  remove: (id: number) => `${BASE_URL}/bookmarks/${id}`,
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

// Register Data Extract Endpoint Paths Here
export const dataExtractPath = {
  extract: () => DATA_EXTRACT_BASE_URL,
  allActors: "actor/all",
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

export const enumValueBaseUrl = `${BASE_URL}/enum-value`;

export const personBaseUrl = `${BASE_URL}/person`;
export const personPath = {
  getSelf: () => `${personBaseUrl}/self`,
};

export const roleBaseUrl = `${BASE_URL}/role`;
export const rolePath = {
  findAll: () => roleBaseUrl,
  getViewById: (id: number) => `${roleBaseUrl}/view/${id}`,
};

export const staticPanelBaseUrl = `${BASE_URL}/static-panel`;
export const staticPanelPath = {
  load: () => staticPanelBaseUrl,
  save: () => staticPanelBaseUrl,
  findByGroupKey: (groupKey: string) => `${staticPanelBaseUrl}/group?group=${groupKey}`,
};

// Endpoints: User Management Panel
export const userBaseUrl = `${BASE_URL}/user`;
export const userPath = {
  findAll: () => userBaseUrl,
  whoami: () => `${userBaseUrl}/whoami`,
  getByUserId: (userId: string) => `${userBaseUrl}/user-id/${userId}`,
  updateRoles: (userName: string) => `${userBaseUrl}/${userName}/roles`,
  register: () => `${userBaseUrl}/new-user`,
  resetPassword: () => `${userBaseUrl}/reset-password`,
  bulkUploadPreview: (mode: string) => `${userBaseUrl}/bulk/${mode}/preview`,
  bulkUpload: (mode: string) => `${userBaseUrl}/bulk/${mode}/upload`,
  deleteUser: (username: string) => `${userBaseUrl}/${username}`,
};
