// Define TypeScript interfaces for props and data structures

import { EntityKind } from "./Entity";

export type TableRow = {
    [key: string]: any; // Generic row structure, to be resolved dynamically
};
export interface BaseKind {
    id: string | number;
    name: string;
    description: string;
}
export interface ComplexityKind extends BaseKind {
    kind: string;
    isDefault: boolean;
    externalId: string;
}

export interface CostKind extends BaseKind {
    costKind: string;
}

export type GridColumn = {
    field: string;
    name: string;
    width: string;
    maxLength?: number;
};

export type KindGridProps = {
    onSelect: (row: TableRow) => void; // Callback invoked when a row is selected
    selectionFilter: (kind: ComplexityKind) => boolean; // Filter function for row data
};

export type GridProps = {
    columnDefs: GridColumn[]; // Column definitions for the table
    rowData: TableRow[]; // Data for rows
    onSelectRow: (row: TableRow) => void; // Callback to select a row
};
export interface PickerGridProps {
    subjectKind: EntityKind;
    onSelect: (row: any) => void; // Callback for row selection
    selectionFilter: (row: CostKind) => boolean;
}
