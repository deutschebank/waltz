import React ,{useMemo,useState} from "react";
import Day from "../day/Day";
import {dimensions, monthNames, MonthBlock, DayCell} from "../calendar-heatmap-utils";
import Weeks from "../weeks/Weeks"
import styles from "./Month.module.css";

export interface MonthProps {
    monthData:MonthBlock;
    dayFillColor: (v:number) => string;
    onSelectDate: (d:Date) => void;
    onSelectWeek: (ds: Date[]) => void;
    labelColor: string;
}

function mkTranslate(i:number,offset:number,cell:number) {
    const dayOffset = i+ offset;
    const dx = (dayOffset % 7) * cell;
    const dy = Math.floor(dayOffset/7)*cell;
    return {dx,dy};
}

const Month: React.FC<MonthProps> = ({
    monthData,
    dayFillColor,
    onSelectDate,
    onSelectWeek,
    labelColor,
}) => {
    const [hoveredDay,setHoveredDay] = useState<number | null>(null);
            return (
        <g className={styles.monthGroup}>

            {/*Month label*/}
            <text
                className={styles.monthHeaderText}
                transform={`translate(${(7* dimensions.day.width)/2})`}
                textAnchor="middle"
                dx={dimensions.day.width/4}
                dy={"20"}
                fill={labelColor}
                pointerEvents="none"
            >
                {monthNames[monthData.startDate.getMonth()]}
            </text>
            {/*Weekend Background strips*/}
            <g transform={`translate(15,40)`} className={styles.monthHeaderStrip}>
                <Weeks monthData={monthData} hoveredDay={hoveredDay} onSelectWeek={onSelectWeek}/>
            </g>
            {/*Day Circles*/}
            <g className={styles.monthBody} transform={`translate(15,40)`}>
                <>
                    {monthData.days.map((day:DayCell,i:number) => {
                        const offset = useMemo(()=> new Date(monthData.startDate).getDay(),[monthData.startDate]);
                        const {dx,dy} = mkTranslate(i, offset,dimensions.day.width);
                        const hasValue = day.value !== 0;
                        const fill = hasValue? dayFillColor(day.value) : "#eee";
                        const stroke = hoveredDay === i ? "black" : "#bbb";
                        const dayNum=day.date.getDate();
                        return (
                            <g key={day.date.toISOString()}
                               transform={`translate(${dx}, ${dy})`}
                               onMouseEnter={()=> setHoveredDay(i)}
                               onMouseLeave={()=> setHoveredDay(null)}
                               className={styles.dayWrapper}
                            >
                                <Day date={day.date}
                                     color={fill}
                                     stroke= {stroke}
                                     onSelect={hasValue?onSelectDate:()=>{}}
                                     className={`${styles.day} ${hasValue?styles.activeDay:styles.inactiveDay}`}
                                />
                                <text className={styles.dayNumber}
                                      textAnchor="middle"
                                      alignmentBaseline="middle"
                                      dy="0.15em"
                                      fill="#333"
                                      style={{pointerEvents:"none"}}
                                >
                                    {dayNum}
                                </text>
                            </g>
                        );
                    })}
                </>
            </g>
        </g>
    );
};

export default Month;
