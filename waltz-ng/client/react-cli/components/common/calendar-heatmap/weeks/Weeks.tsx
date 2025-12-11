import React ,{useMemo,useState} from "react";

import {daysInMonth,dimensions,MonthBlock} from "../calendar-heatmap-utils";

export interface WeeksProps {
    monthData :MonthBlock;
    hoveredDay: number | null;
    onSelectWeek: (dates: Date[]) => void;
}

function determineWeeks(totalBlocks: number) : number[] {
    if (totalBlocks <=28) return [0,1,2,3];
    if (totalBlocks <= 35) return [0,1,2,3,4];
    return [0,1,2,3,4,5];
}

const Weeks: React.FC<WeeksProps> = ({ monthData, hoveredDay, onSelectWeek}) => {
    const [hoveredWeek, setHoveredWeek] = useState<number | null>(null);

    const offset = monthData.startDate.getDay();
    const month = monthData.startDate.getMonth()+1;
    const year = monthData.startDate.getFullYear();
    const day = monthData.startDate.getDate();
    const numberDays = daysInMonth(month, year);
    const totalBlocks = numberDays + offset;

    const weeks = determineWeeks(totalBlocks);

    const weekDayInfo = useMemo(()=> {
        const map = new Map<number,Date[]>();
        monthData.days.forEach((d,i) => {
            const weekIndex: number = Math.floor((i+offset)/7);
            if (!map.has(weekIndex)){map.set(weekIndex,[])}
            map.get(weekIndex)!.push(d.date);
        });
        return map;
    },[monthData.days,offset])

    const highlightedWeek = hoveredDay ==null? hoveredWeek: Math.floor((offset+hoveredDay)/7);

    return(
        <>
            {weeks.map((week) => (
            <g
                key = {week}
                transform={`translate(${dimensions.weekPadding/2},${dimensions.day.width * week})`}>

                <rect
                    fill={highlightedWeek === week ? " #eee" : "#fff"}
                    className="clickable week"
                    onMouseEnter={()=> setHoveredWeek(week)}
                    onMouseLeave={()=> setHoveredWeek(null)}
                    x={dimensions.weekPadding*-2}
                    // y={`${dimensions.day.width * -1}`}
                    y={-dimensions.day.width/2}
                    width={dimensions.day.width *7 + dimensions.weekPadding }
                    height={dimensions.day.width}
                    onClick={()=>onSelectWeek(weekDayInfo.get(week)??[])}
                    onKeyDown={()=> onSelectWeek(weekDayInfo.get(week)??[])}
                />
            </g>
                ))}
        </>
    )

}

export default Weeks;