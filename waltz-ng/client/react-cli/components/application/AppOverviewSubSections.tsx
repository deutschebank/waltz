import React from "react";
import {ApplicationType} from "../../types/Application";
import {mkRef} from "../../utils/mkRef";
import KeyPeople from "../sub-sections/key-people/KeyPeople";
import AssessmentRatingSubSection from "../sub-sections/assessment-rating/AssessmentRatingSubSection";
import PrimaryViewpointRatings from "../sub-sections/primary-viewpoint-ratings/PrimaryViewpointRatings";

interface AppOverviewSubSections {
    application: ApplicationType
}

const AppOverviewSubSections :React.FC<AppOverviewSubSections> = ({application}) => {
    return (
        <>
            <AssessmentRatingSubSection primaryEntityRef={mkRef(application)}/>
            <br/>
            <KeyPeople primaryEntityRef={mkRef(application)}/>
            <br/>
            <PrimaryViewpointRatings primaryEntityRef={mkRef(application)}/>
        </>
    );
}

export default AppOverviewSubSections;