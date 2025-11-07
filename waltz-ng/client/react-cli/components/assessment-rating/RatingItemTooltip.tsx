import React from "react";
import {AssessmentRating} from "../../types/Assessment";
import {RatingSchemeItemType} from "../../types/Rating";
import styles from "./RatingItemTooltip.module.css";
import DateTime from "../common/DateTime";
import Icon from "../common/Icon";

interface RatingItemTooltipProps {
    assessmentRating: AssessmentRating;
    ratingItem: RatingSchemeItemType;
}

const RatingItemTooltip: React.FC<RatingItemTooltipProps> = ({assessmentRating, ratingItem}) => {
    return (
        <div className={styles.content}>
            <h4>{ratingItem.name}</h4>
            <h6>{ratingItem.description}</h6>
            <hr/>
            {!!assessmentRating.comment && <div className="small">
                <span><Icon name="sticky-note-o"/></span>
                {assessmentRating.comment}
            </div>}
            <div className="small text-muted">
                <span>Last modified by {assessmentRating.lastUpdatedBy}, </span>
                <DateTime dateTime={assessmentRating.lastUpdatedAt}
                          relative={true}/>
            </div>
        </div>
    );
}

export default RatingItemTooltip;