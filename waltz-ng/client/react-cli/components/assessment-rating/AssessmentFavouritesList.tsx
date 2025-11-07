import React from "react";
import Icon from "../common/Icon";
import {RatingIndicatorCell} from "../common/assessment-rating/RatingIndicatorCell";
import {AssessmentDefinition, AssessmentRating} from "../../types/Assessment";
import {RatingSchemeType} from "../../types/Rating";
import Tooltip from "../common/tooltip/Tooltip";
import Markdown from "../common/markdown/Markdown";
import RatingItemTooltip from "./RatingItemTooltip";

interface AssessmentFavouritesListProps {
    assessmentDefinitions: AssessmentDefinition[];
    ratingSchemes: RatingSchemeType[];
    assessmentRatings: AssessmentRating[];
    favouriteAssessmentDefinitions: AssessmentDefinition[];
}

const AssessmentFavouritesList: React.FC<AssessmentFavouritesListProps> = ({assessmentDefinitions,
                                                                               ratingSchemes,
                                                                               assessmentRatings,
                                                                               favouriteAssessmentDefinitions}) => {

    const favouriteDefinitions = assessmentDefinitions?.filter(def =>
        favouriteAssessmentDefinitions?.some(favDef => favDef.id === def.id)
    );


    return (
        <table className="table table-hover table-condensed">
                        <colgroup>
                            <col width="50%"/>
                            <col width="50%"/>
                        </colgroup>
                        <tbody>
                        {favouriteDefinitions?.map((definition) => {
                            const ratingsForDefinition = assessmentRatings?.filter(
                                (rating) => rating.assessmentDefinitionId === definition.id
                            );
                            const ratingScheme = ratingSchemes?.find(
                                (scheme) => scheme.id === definition.ratingSchemeId
                            );

                            return (
                                <tr key={definition.id}>
                                    <td>
                                        {definition.name}
                                        {definition.isReadOnly && <Icon name="lock"/>}
                                    </td>
                                    <td>
                                    <ul key={definition.id} className="list-unstyled">
                                            {ratingsForDefinition && ratingsForDefinition.length > 0 ? (
                                                ratingsForDefinition.map((rating) => {
                                                    const ratingItem = ratingScheme?.ratings.find(
                                                        (item) => item.id === rating.ratingId
                                                    );
                                                    if (!ratingItem) {
                                                        return null;
                                                    }
                                                    return (
                                                        <Tooltip key={ratingItem.id} content={<RatingItemTooltip assessmentRating={rating}
                                                                                             ratingItem={ratingItem}/>}
                                                                                             showTooltip={!!rating.comment}
                                                                                             placement="left">
                                                            <span style={{display: "flex", gap: "0.5rem"}}>
                                                                <RatingIndicatorCell
                                                                            rating={ratingItem}
                                                                            showName={true}/>
                                                                <Icon name="sticky-note-o"/>
                                                            </span>
                                                        </Tooltip>
                                                    );
                                                })
                                            ) : (
                                                <li className="text-muted">Not Provided</li>
                                            )}
                                        </ul>
                                    </td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
    );
}

export default AssessmentFavouritesList;