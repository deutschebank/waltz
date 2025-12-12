import {EntityKind, EntityReference} from "./Entity";
import {IsReadOnlyProvider, LastUpdatedProvider, ProvenanceProvider} from "./Providers";
import {Person} from "./Person";

export interface InvolvementType extends ProvenanceProvider, IsReadOnlyProvider{
    entityReference: EntityReference;
    employee_id: number;
    kind_id: number;
}

export interface InvolvementKindType extends LastUpdatedProvider{
    id: number;
    name: string;
    description: string;
    externalId: string;
    subjectKind: EntityKind;
    userSelectable: boolean;
    permittedRole: string;
    transitive: boolean;
}

export interface InvolvementDetailType {
    involvement: InvolvementType;
    involvementKind: InvolvementKindType;
    person: Person
}

export interface InvolvementByDirectionType {
    ancestors: InvolvementDetailType[];
    descendents: InvolvementDetailType[];
    exact: InvolvementDetailType[];
}

export interface InvolvementPersonType {
    involvement: InvolvementType;
    person: Person
}
