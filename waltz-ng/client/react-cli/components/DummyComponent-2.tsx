import React, {useEffect, useMemo, useState} from "react"

import {HeatInput} from "./common/calendar-heatmap/calendar-heatmap-utils";
import CalendarHeatmap from "./common/calendar-heatmap/CalendarHeatmap";
import AllocationSchemePicker from "./common/Picker/AllocationSchemePicker";
import {AllocationScheme, TableRow} from "../types/Grid";

interface DummyComponentProps {
    helloText?: string;
}

const DummyComponentTwo :React.FC = () => {


    //Allocation Scheme Picker
    const [selectedAllocationScheme, setSelectedAllocationScheme] = useState<
        any[]
    >([
        {
            name: "Mike",
            description: "Lorem Ipsume text !",
        },
    ]); // Store for selected Allocation Scheme
    const onSelectAllocationScheme = (allocationScheme: TableRow) => {
        if (!selectedAllocationScheme.some((as) => as.id === allocationScheme.id)
        ) {
            setSelectedAllocationScheme([
                ...selectedAllocationScheme,
                allocationScheme,
            ]); // Append if not already selected
        }};
    // Filter logic for AllocationSchemePicker
    const allocationSchemeIds = selectedAllocationScheme.map((as) => as.id);
    const selectionFilterAllocationScheme = (allocationScheme: AllocationScheme) =>
        !allocationSchemeIds.includes(allocationScheme.id);


    const today = new Date();
    const start = new Date(today.getFullYear()-1,today.getMonth()+1,1)

    const data = useMemo(()=> genTestDataForCalendarHeatmap(start,today),[start,today]);


    return (
        <div>

            <h5>3. Allocation Scheme Picker</h5>
            <AllocationSchemePicker
                onSelect={onSelectAllocationScheme}
                selectionFilter={selectionFilterAllocationScheme}
            />

            <CalendarHeatmap
                data={data}
                onSelectDate={(d) => console.log("Selected Date: ", d)}
                onSelectWeek={(ds) => console.log("Selected Week dates: ", ds)}
                onSelectMonth={(ds) => console.log("Selected month dates: ", ds)}
                title={"Calender Heatmap"}
            />
        </div>
    );
};

function genTestDataForCalendarHeatmap(start: Date, end: Date): HeatInput[] {
    const out: HeatInput[] = [];
    const d = new Date(start);
    while (d <= end) {
        const count = Math.random() < 0.28 ? Math.floor(Math.random()*6):0;
        const yyyy = d.getFullYear();
        const mm = String(d.getMonth()+1).padStart(2,"0");
        const dd = String(d.getDate()).padStart(2,"0");
        out.push({date: `${yyyy}-${mm}-${dd}`,count});
        d.setDate(d.getDate()+1);
    }
    return out;
}

export default DummyComponentTwo;