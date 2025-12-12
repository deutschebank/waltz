import React from "react";
import Icon from "../Icon";
import styles from "./RatingIndicatorCell.module.css";
import {FASize} from "../../../types/FontAwesome";
import {RatingSchemeItemType} from "../../../types/Rating";

export interface RatingIndicatorCellProps {
    rating: RatingSchemeItemType
    showName?: boolean;
    showGroup?: boolean;
    size?: FASize;
}

export function RatingIndicatorCell({
    rating,
    showName = true,
    showGroup = false,
    size = "lg"
}: RatingIndicatorCellProps) {

    return (
        <div title={rating.name} className={styles.ratingIndicator}>
            <div style={{display: "inline-block"}} className={styles.stackBox}>
                <span style={{color: rating.color}}>
                    <Icon size={size} stack={"1x"} name={"square"}/>
                </span>
                <span style={{color: "#aaaaaa"}}>
                    <Icon size={size} stack={"1x"} name={"square-o"}/>
                </span>
            </div>
            <span>
                {showGroup && rating.ratingGroup && (
                    <span className={["group", styles.textMuted].join(" ")}>
                        {rating.ratingGroup} /
                    </span>
                )}
                {showName && (
                    <span className="name">
                        {rating.name}
                    </span>
                )}
            </span>
        </div>
    );
}
