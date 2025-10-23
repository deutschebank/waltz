import React, { useMemo } from "react";
import Grid from "./Grid";
import Icon from "./Icon";
import { GridColumn } from "../../types/Grid";
import { findBySubjectKind } from "../../api/cost-kind";
import { useQuery } from "@tanstack/react-query";

// Interface for cost kind data
export interface CostKind {
    id: string;
    costKind: string;
    name: string;
    description: string;
}

interface GridProps {
    subjectKind: string;
    onSelect: (row: any) => void; // Callback for row selection
    selectionFilter: (row: CostKind) => boolean;
}

const CostKindPicker: React.FC<GridProps> = ({
    subjectKind = "APPLICATION",
    onSelect = () => console.log("Selecting cost kind"),
    selectionFilter = () => true,
}) => {
    // Column definitions
    const columnDefs: GridColumn[] = [
        { field: "name", name: "Cost Kind", width: "30%" },
        { field: "description", name: "Description", width: "70%" },
    ];

    // React Query for fetching the cost kind
    const {
        data: costKinds = [],
        isLoading,
        isError,
    } = useQuery(findBySubjectKind(subjectKind));

    // Derive rowData dynamically based on costKinds
    const rowData = useMemo(() => {
        if (!costKinds) return [];
        return costKinds
            .map((d: any) => d.costKind)
            .filter(selectionFilter)
            .sort((a, b) => a.name.localeCompare(b.name));
    }, [costKinds, selectionFilter]);

    // Handle loading and error states
    if (isLoading) return <p>Loading...</p>;
    if (isError) return <p>Error loading data.</p>;

    return (
        <div>
            {/* Information Block */}
            <div className="help-block small">
                <Icon name="info-circle" />
                <span>
                    Select a cost kind from the list below, you can filter the
                    list using the search bar.
                </span>
            </div>

            {/* Render Grid Component */}
            <Grid
                columnDefs={columnDefs}
                rowData={rowData}
                onSelectRow={onSelect}
            />
        </div>
    );
};

export default CostKindPicker;
