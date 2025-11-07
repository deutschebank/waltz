import {EntityKind} from "../enums/Entity";
import {IsRemovedProvider} from "./Providers";
import {PersonKindEnum} from "../enums/person";

export type PersonKindType = keyof typeof PersonKindEnum;

export interface Person extends IsRemovedProvider{
    id: number;
    employeeId: string;
    displayName: string;
    email: string;
    personKind: PersonKindType;
    title?: string;
    mobilePhone?: string;
    officePhone?: string;
    userPrincipalName?: string;
    managerEmployeeId?: string;
    departmentName?: string;
    organisationalUnitId?: number;
    name: string;
    userId: string;
    kind: EntityKind.PERSON;
}

export interface KeyPersonsAndRole {
    roleName: string;
    persons: Person[];
}
