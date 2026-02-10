import {EntityKind, EntityReference} from "./Entity";
import {LastUpdatedProvider, ProvenanceProvider} from "./Providers";

export interface NoteTypeEntity extends Omit<EntityReference, "kind" | "entityLifecycleStatus"> {
  applicableEntityKinds: EntityKind[];
  position: number;
  isReadOnly: boolean;
}

export interface NamedNoteWithOperations {
  entity: NoteTypeEntity;
  operations: string[];
}

export interface Note
  extends Partial<LastUpdatedProvider>, Partial<ProvenanceProvider> {
  entityReference?: EntityReference;
  namedNoteTypeId?: number;
  noteText: string;
}

export interface NoteWithType {
  type: NoteTypeEntity;
  note: Note;
  operations: string[];
}
