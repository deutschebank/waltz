import React, {useState} from "react";
import AssessmentFavouritesList from "../../assessment-rating/AssessmentFavouritesList";
import SubSection from "../../common/sub-section/SubSection";
import {EntityReference} from "../../../types/Entity";
import {useQuery} from "@tanstack/react-query";
import {assessmentDefinitionApi} from "../../../api/assessment-definition";
import {ratingSchemeApi} from "../../../api/rating-scheme";
import {assessmentRatingApi} from "../../../api/assessment-rating";
import SubSectionLoader from "../../common/loader/sub-section/SubSectionLoader";
import AssessmentRatingSubSectionControls from "./AssessmentRatingSubSectionControls";

interface PrimaryViewpointRatingsProps {
    primaryEntityRef: EntityReference
}

const AssessmentRatingSubSection: React.FC<PrimaryViewpointRatingsProps> = ({primaryEntityRef}) => {
    const {isPending: assessmentDefinitionsPending, data: assessmentDefinitions = []} = useQuery(assessmentDefinitionApi.findByEntityReference(primaryEntityRef));
    const {isPending: ratingSchemesPending, data: ratingSchemes = []} = useQuery(ratingSchemeApi.findAll());
    const {isPending: assessmentRatingsPending, data: assessmentRatings = []} = useQuery(assessmentRatingApi.findForEntityReference(primaryEntityRef));
    const {isPending: favouriteAssessmentsPending, data: favouriteAssessmentDefinitions = []} = useQuery(assessmentDefinitionApi.findFavouritesForUser());

    const isLoading = assessmentDefinitionsPending || ratingSchemesPending || assessmentRatingsPending || favouriteAssessmentsPending;

    const favouriteAssessments = !isLoading && <AssessmentFavouritesList assessmentDefinitions={assessmentDefinitions}
                                                           ratingSchemes={ratingSchemes}
                                                           assessmentRatings={assessmentRatings}
                                                           favouriteAssessmentDefinitions={favouriteAssessmentDefinitions}/>;

    return (
        <>
            {isLoading && <SubSectionLoader/>}
            {!isLoading && <SubSection header="Assessments"
                         content={favouriteAssessments}
                         controls={<AssessmentRatingSubSectionControls/>}/>}
        </>
    );
}

export default AssessmentRatingSubSection;