// Define TypeScript interfaces for props and data structures

export type TableRow = {
    [key: string]: any; // Generic row structure, to be resolved dynamically
};

export type ComplexityKind = {
    id: number;
    name: string;
    description: string;
    kind: string;
    isDefault: boolean;
    externalId: string;
    costKind?: string;
};

export type GridColumn = {
    field: string;
    name: string;
    width: string;
    maxLength?: number;
};

export type KindGridProps = {
    // subjectKind?: string;
    onSelect: (row: TableRow) => void; // Callback invoked when a row is selected
    selectionFilter: (kind: ComplexityKind) => boolean; // Filter function for row data
};

export type GridProps = {
    columnDefs: GridColumn[]; // Column definitions for the table
    rowData: TableRow[]; // Data for rows
    onSelectRow: (row: TableRow) => void; // Callback to select a row
};
