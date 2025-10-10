
export interface Person {
    id: number;
    employeeId: string;
    displayName: string;
    email: string;
    isRemoved: boolean;
    personKind: "EMPLOYEE" | "CONTRACTOR" | "UNKNOWN";
    title?: string;
    mobilePhone?: string;
    officePhone?: string;
    userPrincipalName?: string;
    managerEmployeeId?: string;
    departmentName?: string;
    organisationalUnitId?: number;
    name: string;
    userId: string;
    kind: "PERSON";
}
