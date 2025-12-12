import React from "react";
import {MeasurableRatingType} from "../../../types/MeasurableRating";
import {RatingSchemeItemType} from "../../../types/Rating";
import {MeasurableType} from "../../../types/Measurable";
import Markdown from "../../common/markdown/Markdown";
import DateTime from "../../common/DateTime";

interface PrimaryRatingTooltipContentProps {
    rating: MeasurableRatingType;
    ratingSchemeItem: RatingSchemeItemType;
    measurable: MeasurableType;
}

const PrimaryRatingTooltipContent: React.FC<PrimaryRatingTooltipContentProps> = ({
    rating,
    ratingSchemeItem,
    measurable
}) => {
    return (
        <div>
            <p className="lead" style={{marginBottom: "0.5rem"}}>
                {measurable.name}: {ratingSchemeItem.name}
            </p>
            <Markdown text={rating.description}/>
            <hr/>
            <p className="small text-muted">
                Last updated by {rating.lastUpdatedBy}, <DateTime dateTime={rating.lastUpdatedAt}/>
            </p>
        </div>
    );
};

export default PrimaryRatingTooltipContent;