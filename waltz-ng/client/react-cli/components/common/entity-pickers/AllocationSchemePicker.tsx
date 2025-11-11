import React, { useMemo } from "react";

import Grid from '../Grid';
import Icon from '../Icon';
import {findAll} from "../../../api/allocation-scheme";
import {GridColumn} from "../../../types/Grid";
import {useQuery} from "@tanstack/react-query";
import Loader from "../loader/Loader";

export interface AllocationScheme {
    id: string | number
    name: string;
    description: string;
}

interface ColumnDef {
    field: string;
    name: string;
    width?: string;
    maxLength?: number;
}

interface AllocationSchemePickerProps {
    onSelect?: (row: AllocationScheme) => void;
    selectionFilter?: (row: AllocationScheme) => boolean;
}

const AllocationSchemePicker: React.FC<AllocationSchemePickerProps> =
    ({
         onSelect = () => console.log("Selecting allocation scheme kind"),
         selectionFilter = () => true,
     }) => {

        const {
            data: allocationSchemes,
            isLoading,
            isError
        } = useQuery(findAll())



        const rowData = useMemo<AllocationScheme[]>(() => {
            const list = allocationSchemes??[];
            return list.filter(selectionFilter)
                .sort((a,b) => a.name.localeCompare(b.name))
        }, [allocationSchemes,selectionFilter]);


        const columnDefs: GridColumn[] = [
            { field: "name", name: "Allocation Scheme", width: "30%" },
            { field: "description", name: "Description", width: "70%", maxLength: 300 }
        ];


        if (isError) {
            return (
                <div className="help-block small" style = {{color: 'crimson'}}>
                    Failed to load Allocation Schemes
                </div>
            );
        }

        return (
            <div>
                {isLoading ? (
                    <Loader />
                ) : (
                    <>
                        <div className="help-block small">
                            <Icon name="info-circle" />
                            Select an allocation scheme from the list below, you can filter the list using the search bar.
                        </div>
                        <br />
                        <Grid
                            columnDefs={columnDefs}
                            rowData={rowData}
                            onSelectRow={(row) => onSelect(row as AllocationScheme)} />
                    </>)}
            </div>
        );
    };

export default AllocationSchemePicker;