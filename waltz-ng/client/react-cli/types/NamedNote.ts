import {EntityKind, EntityReference} from "./Entity";
import {LastUpdatedProvider, ProvenanceProvider} from "./Providers";

export interface NoteTypeEntity {
  id: number;
  description: string;
  name: string;
  externalId: string;
  applicableEntityKinds: EntityKind[];
  position: number;
  isReadOnly: boolean;
}

export interface TypeWithOperations {
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
