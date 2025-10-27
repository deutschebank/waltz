import React, { useState, useMemo } from "react";
import { chain, sumBy, filter, isEmpty, get } from "lodash";
import Icon from "./Icon";
import { truncateMiddle } from "../../../common/string-utils";
import { GridColumn, GridProps, TableRow } from "../../types/Grid";

const Grid: React.FC<GridProps> = ({ columnDefs, rowData, onSelectRow }) => {
    // State for managing the search query
    const [qry, setQry] = useState<string>("");

    // Function to calculate truncated cell value
    const getColVal = (col: GridColumn, row: TableRow): string => {
        const rawVal = get(row, col.field.split(".").join(""), "-");
        return col.maxLength
            ? truncateMiddle(rawVal, col.maxLength) || rawVal
            : rawVal;
    };

    // Function to get column title (for hover information)
    const getColTitle = (col: GridColumn, row: TableRow): string | null => {
        return col.field ? get(row, col.field.split(".").join(""), null) : null;
    };

    // Derived state for filtered rows based on query
    const filteredRows = useMemo(() => {
        if (isEmpty(qry)) return rowData;
        const lowerQry = qry.toLowerCase();
        return chain(rowData)
            .filter((row) =>
                columnDefs.some((col) =>
                    getColVal(col, row).toLowerCase().includes(lowerQry)
                )
            )
            .value();
    }, [qry, rowData, columnDefs]);

    // Calculate unallocated width for dynamic columns
    const totalAllocatedWidth = useMemo(
        () =>
            sumBy(columnDefs, (col) =>
                typeof col.width === "string"
                    ? parseInt(col.width)
                    : col.width || 0
            ),
        [columnDefs]
    );

    const unallocatedColumns = useMemo(
        () => filter(columnDefs, (col) => !col.width),
        [columnDefs]
    );

    const unallocatedWidth = isEmpty(unallocatedColumns)
        ? 0
        : (100 - totalAllocatedWidth) / unallocatedColumns.length;

    return (
        <div>
            {/* Search Bar */}
            <div className="row" style={{ paddingBottom: "1em" }}>
                <div className="col-sm-12">
                    <input
                        className="grid-search"
                        style={{ display: "inline-block" }}
                        type="text"
                        placeholder="Search..."
                        value={qry}
                        onChange={(e) => setQry(e.target.value)} // Update search query
                    />
                    <span>{isEmpty(qry) ? "" : "searching"}</span>
                    <Icon name="search" /> {/* Search icon */}
                </div>
            </div>

            {/* Table containing rows and columns */}
            <div className="row">
                <div
                    className={`col-sm-12 ${
                        isEmpty(rowData) ? "" : "waltz-scroll-region-300"
                    }`}
                >
                    <table className="table table-condensed table-hover small fixed-table">
                        {/* Column Group */}
                        <colgroup>
                            {columnDefs.map((col, idx) => (
                                <col
                                    key={idx}
                                    width={`${get(
                                        col,
                                        "width",
                                        `${unallocatedWidth}%`
                                    )}`}
                                />
                            ))}
                        </colgroup>

                        {/* Table Header */}
                        <thead>
                            <tr>
                                {columnDefs.map((col, idx) => (
                                    <th key={idx}>{col.name}</th>
                                ))}
                            </tr>
                        </thead>

                        {/* Table Body */}
                        <tbody>
                            {filteredRows.map((row, rowIdx) => (
                                <tr
                                    key={rowIdx}
                                    className="clickable"
                                    onClick={() => onSelectRow(row)} // Emit row selection event
                                >
                                    {columnDefs.map((col, colIdx) => (
                                        <td
                                            key={colIdx}
                                            title={
                                                getColTitle(col, row) ||
                                                undefined
                                            } // Cell hover title
                                        >
                                            {getColVal(col, row)}{" "}
                                            {/* Render truncated cell value */}
                                        </td>
                                    ))}
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default Grid;
