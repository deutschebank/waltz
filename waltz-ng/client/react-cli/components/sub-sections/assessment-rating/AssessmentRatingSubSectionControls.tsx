import React from "react";
import {useJumpTo} from "../../../hooks/useJumpTo";
import Tooltip from "../../common/tooltip/Tooltip";

const AssessmentRatingSubSectionControls: React.FC = () => {
    const jumpTo = useJumpTo();
    const tooltipContent = <p>Opens section to view/edit all assessments</p>;

    return (
        <Tooltip content={tooltipContent}
                 placement="left">
            <a className="clickable pull-right"
               waltz-jump-to="involved-people-section"
               onClick={() => jumpTo("assessment-rating-section")}>
                More
            </a>
        </Tooltip>
    );
}

export default AssessmentRatingSubSectionControls;