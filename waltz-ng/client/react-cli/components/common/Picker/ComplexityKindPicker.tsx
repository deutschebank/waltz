import React, { useMemo } from "react";
import Grid from "../Grid";
import Icon from "../Icon";
import { useQuery } from "@tanstack/react-query";
import { findAll } from "../../../api/complexity-kind";
import { KindGridProps, GridColumn } from "../../../types/Grid";
import Loader from "../loader/Loader";

const ComplexityKindPicker: React.FC<KindGridProps> = ({
    onSelect = () => console.log("Selecting complexity kind"),
    selectionFilter = () => true,
}) => {
    // React Query for fetching the complexity kinds
    const {
        data: complexityKinds = [],
        isLoading,
        isError,
    } = useQuery(findAll());

    // Memoize filtered and ordered rowData (derived state)
    const rowData = useMemo(() => {
        if (!complexityKinds) return [];
        return complexityKinds
            .filter(selectionFilter)
            .sort((a, b) => a.name.localeCompare(b.name));
    }, [complexityKinds, selectionFilter]);

    // Grid column definitions (similar to Svelte's columnDefs)
    const columnDefs: GridColumn[] = [
        { field: "name", name: "Complexity Kind", width: "30%" },
        {
            field: "description",
            name: "Description",
            width: "70%",
            maxLength: 300,
        },
    ];

    // Handle error states
    if (isError) return <p>Error loading data.</p>;

    return (
        <div>
            {isLoading ? (
                <Loader />
            ) : (
                <>
                    {/* Info block */}
                    <div className="help-block small">
                        <Icon name="info-circle" />
                        <span>
                            Select a complexity kind from the list below, you
                            can filter the list using the search bar.
                        </span>
                    </div>
                    <br />

                    {/* Grid component */}
                    <Grid
                        columnDefs={columnDefs} // Pass column definitions
                        rowData={rowData} // Pass filtered and sorted data
                        onSelectRow={onSelect} // Pass row selection handler
                    />
                </>
            )}
        </div>
    );
};

export default ComplexityKindPicker;
