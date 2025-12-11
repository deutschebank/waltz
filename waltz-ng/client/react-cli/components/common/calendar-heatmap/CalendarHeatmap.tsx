import React, { useState, useMemo, useCallback } from "react";
import styles from "./calendarHeatmap.module.css";
import {
    prepareMonthData,
    dimensions,
    HeatInput,
    MonthBlock,
} from "./calendar-heatmap-utils";
import CalenderHeatmapControlPanel from "./calendar-heatmap-control-panel/CalendarHeatmapControlPanel";
import Month from "./month/Month";

function formatYYYMM(date: Date):string{
    const year = date.getFullYear();
    const month= String(date.getMonth()+1).padStart(2,"0");
    return `${year}-${month}`;
}

function sqrtScale(d0:number,d1:number) {
    const a = Math.sqrt(Math.max(0,d0));
    const b = Math.sqrt(Math.max(a,d1 || 1));
    return (x:number) => {
        const v = Math.sqrt(Math.max(0,x));
        return Math.min(1,Math.max(0,(v-a)/(b-a || 1)));
    }
}

function lerpHex(a:string,b:string, t:number) {
    const A = a.replace("#","") , B= b.replace("#","")
    const ar = parseInt(A.slice(0,2),16), ag = parseInt(A.slice(2,4),16) , ab = parseInt(A.slice(4,6),16);
    const br = parseInt(B.slice(0,2),16), bg = parseInt(B.slice(2,4),16) , bb = parseInt(B.slice(4,6),16);
    const rr = Math.round(ar+(br-ar)*t).toString(16).padStart(2,"0");
    const rg = Math.round(ag+(bg-ag)*t).toString(16).padStart(2,"0");
    const rb = Math.round(ar+(bb-ab)*t).toString(16).padStart(2,"0");
    return `#${rr}${rg}${rb}`
}


interface CalendarHeatmapProps {
    data: HeatInput[];
    onSelectDate?: (date: Date) => void;
    onSelectWeek?: (dates: Date[]) => void;
    onSelectMonth?: (dates: Date[]) => void;
    title?: string

}

export function CalendarHeatmap({
                        data ,
                        onSelectDate = (x) => console.log("selecting date", x),
                        onSelectWeek = (x) => console.log("selecting week", x),
                        onSelectMonth = (x) => console.log("selecting month", x),
    title="Calendar Heatmap",}:CalendarHeatmapProps){

    const today = new Date();
    const [startDate, setStartDate] = useState(new Date(today.getFullYear() - 1, today.getMonth() + 1, 1));
    const [endDate, setEndDate] = useState<Date>(today);
    const [hoveredMonth, setHoveredMonth] = useState<number | null>(null);
    const months: MonthBlock[] = useMemo(
        () => prepareMonthData(data??[],startDate,endDate),
        [data,startDate,endDate]
    );

    const maxValue = useMemo(()=> Math.max(0,...data.map(d=>d.count)),[data]);

    const dayFillColor = useMemo(()=> {
        const s = sqrtScale(0,maxValue || 1);
        return (v:number) => lerpHex("#e7fae2","#07ed4a",s(v));
    },[maxValue]);

    const monthLabelColor = useMemo(()=>{
        const s = sqrtScale(0,Math.max(1,months.length));
        return (i:number) => lerpHex("#b7b7b7","#666666",s(i));
    },[months.length]);

    const rows = useMemo(() => Math.ceil(months.length / dimensions.monthsPerline), [months]);
    const vbWidth = useMemo(() => dimensions.month.width * dimensions.monthsPerline, []);
    const vbHeight = useMemo(() => dimensions.month.height * rows, [rows]);

    const handleSelectMonth = useCallback((idx:number) => {
        const m = months[idx];
        onSelectMonth(m.days.map(d=>d.date));
    },[months,onSelectMonth]);

    return (
        <div className={styles.root}>
            <h2 style = {{fontWeight:"bold"}}>{title}</h2>
            <svg className={styles.svg} width={"100%"} viewBox={`0 0 ${vbWidth} ${vbHeight}`} preserveAspectRatio="xMinYmin meet">
                <>
                    {months.map((m, idx) => {
                        const tx = (idx % dimensions.monthsPerline) * dimensions.month.width;
                        const ty = Math.floor(idx / dimensions.monthsPerline) * dimensions.month.height;
                        return (
                    <svg
                        className={styles.svg}
                        key={m.startDate.toISOString()}
                        x={tx}
                        y={ty}
                        width={dimensions.month.width}
                        height={dimensions.month.height}
                        overflow="visible"
                    >
                        <rect x={0} y={0} width={dimensions.month.width} height={dimensions.month.height} fill="none" stroke="transparent"/>
                                {/*Month header strip(hover+click*/}
                                <g onClick={() => handleSelectMonth(idx)}
                                   onMouseEnter={() => setHoveredMonth(idx)}
                                   onMouseLeave={() => setHoveredMonth(null)}
                                   role={"button"}>
                                    <title>{formatYYYMM(m.startDate)}</title>
                                    <rect
                                        width={dimensions.month.width}
                                        height={30}
                                        className="clickable"
                                        // onMouseEnter={() => setHoveredMonth(idx)}
                                        // onMouseLeave={() => setHoveredMonth(null)}
                                        fill={hoveredMonth === idx ? "#eee" : "#fff"}
                                    />
                                </g>
                                {/*Month Body*/}
                                <Month
                                    monthData={m}
                                    dayFillColor={dayFillColor}
                                    onSelectDate={onSelectDate}
                                    onSelectWeek={onSelectWeek}
                                    labelColor={monthLabelColor(idx)}
                                />
                                {/*Hover Outline*/}
                                <rect
                                    stroke={hoveredMonth === idx ? "#ddd" : "none"}
                                    fill="none"
                                    x="1"
                                    y="1"
                                    rx="2"
                                    ry="2"
                                    width={dimensions.month.width - 2}
                                    height={dimensions.month.height - 2}
                                    // style={{ pointerEvents: "none" }}
                                />
                            </svg>
                        );
                    })}
                </>

                        </svg>

            <CalenderHeatmapControlPanel startDate={startDate}
                                         endDate={endDate}
                                         setStartDate={(d) => setStartDate( d instanceof Date? d: new Date(d))}
                                         setEndDate=  {(d) => setEndDate( d instanceof Date? d: new Date(d))}
            />
                        </div>
                        )
                    }
export default CalendarHeatmap;