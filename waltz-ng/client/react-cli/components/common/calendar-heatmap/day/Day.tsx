import React from "react";
import styles from "./Day.module.css"
import { dimensions} from "../calendar-heatmap-utils"

export interface DayProps {
    date: Date;
    color: string;
    stroke: string;
    onSelect: (date:Date) => void;
    className?: string;
}

const Day: React.FC<DayProps> = ({date,color,stroke,onSelect,className}) => {
    const isFuture = date > new Date();
    return (
        <circle
            className={className}
            r={dimensions.circleRadius}
            fill={color}
            stroke={stroke}
            onClick={()=>onSelect(date)}
            onKeyDown={()=>onSelect(date)}
        />
    )
};

export default Day;