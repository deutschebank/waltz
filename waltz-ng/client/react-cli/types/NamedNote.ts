import {EntityReference} from "./Entity";
import {LastUpdatedProvider, ProvenanceProvider} from "./Providers";

export interface NamedNote {
    entity: {
        id: number;
        description: string;
        name: string;
        externalId: string;
        applicableEntityKinds: string[];
        position: number;
        isReadOnly: boolean;
    };
    operations: [];
}

export interface NamedNoteType extends Partial<LastUpdatedProvider>, Partial<ProvenanceProvider> {
    entityReference?: EntityReference;
    namedNoteTypeId?: number;
    noteText: string;
}

export interface Note {
    type: NamedNote["entity"];
    note: NamedNoteType;
}