import {execute, fetchJSON} from "./api";
import {EntityReference} from "../types/Entity";
import {NamedNote, NamedNoteType} from "../types/NamedNote";
import {notesPath} from "../constants/path";

const findForEntityReference = (ref: EntityReference) => ({
    queryKey: ["entity-named-note", "findForEntityReference", ref],
    queryFn: async (): Promise<NamedNoteType[]> => {
        return await fetchJSON(notesPath.findForEntityReference(ref));
    },
    enabled: !!ref,
});

const remove = (entityRef: EntityReference, noteTypeId: number) => ({
    mutationKey: ["entity-named-note", "remove", entityRef, noteTypeId],
    mutationFn: async (): Promise<boolean> => {
        return await execute(notesPath.remove(entityRef, noteTypeId), "DELETE");
    },
});

const save = (entityRef: EntityReference, noteTypeId: number, noteText: string) => ({
    mutationKey: ["entity-named-note", "save", entityRef, noteTypeId],
    mutationFn: async (): Promise<boolean> => {
        return await execute(notesPath.save(entityRef, noteTypeId), "PUT", noteText);
    },
});

export const noteApi = {
    findForEntityReference,
    remove,
    save,
};
