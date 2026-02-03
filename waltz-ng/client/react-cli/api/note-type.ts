import {fetchJSON} from "./api";
import {EntityReference} from "../types/Entity";
import {notesTypePath} from "../constants/path";
import {NamedNoteWithOperations} from "../types/NamedNote";

const getByExternalId = (externalId: string) => ({
  queryKey: ["entity-named-note-type", "getByExternalId", externalId],
  queryFn: async (): Promise<NamedNoteWithOperations> => {
    return await fetchJSON(notesTypePath.getByExternalId(externalId));
  },
  enabled: !!externalId,
});

const findForRefAndUser = (ref: EntityReference) => ({
  queryKey: ["entity-named-note-type", "findForRefAndUser", ref],
  queryFn: async (): Promise<NamedNoteWithOperations[]> => {
    return await fetchJSON(notesTypePath.findForRefAndUser(ref));
  },
  enabled: !!ref,
});

export const noteTypeApi = {
  getByExternalId,
  findForRefAndUser,
};
