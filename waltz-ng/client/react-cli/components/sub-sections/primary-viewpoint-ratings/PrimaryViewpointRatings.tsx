import React from "react";
import SubSection from "../../common/sub-section/SubSection";
import PrimaryViewpointRatingsControls from "./PrimaryViewpointRatingsControls";
import {EntityReference} from "../../../types/Entity";
import {useQuery} from "@tanstack/react-query";
import {measurableRatingApi} from "../../../api/measurable-rating";
import {SelectionOptions} from "../../../types/SelectionOptions";
import SubSectionLoader from "../../common/loader/sub-section/SubSectionLoader";
import Tooltip from "../../common/tooltip/Tooltip";
import {RatingIndicatorCell} from "../../common/assessment-rating/RatingIndicatorCell";
import Icon from "../../common/Icon";
import PrimaryRatingTooltipContent from "./PrimaryRatingTooltipContent";
import {MeasurableType,MeasurableCategoryType} from "../../../types/Measurable";
import {RatingSchemeItemType} from "../../../types/Rating";
import {MeasurableRatingType, MeasurableRatingViewType} from "../../../types/MeasurableRating";

interface PrimaryViewpointRatingsProps {
    primaryEntityRef: EntityReference
}

interface RatingRow {
    rating: MeasurableRatingType,
    measurable: MeasurableType,
    category: MeasurableCategoryType,
    ratingSchemeItem: RatingSchemeItemType
}

const PrimaryViewpointRatings: React.FC<PrimaryViewpointRatingsProps> = ({primaryEntityRef}) => {
    const options: SelectionOptions = {
        entityReference: primaryEntityRef,
        scope: "EXACT"
    }

    const {isPending: ratingsPending, data: primaryRatings} = useQuery(measurableRatingApi.getPrimaryRatingsViewBySelector(options))

    const content = () => {
        if (!primaryRatings) {
            return (
                <div className="help-block">
                    <Icon name="info-circle"/> No primary ratings have been set for this application
                </div>
            );
        }

        const measurablesById = new Map(primaryRatings
            .measurables
            .map(measurable => [measurable.id, measurable]))

        const categoriesById = new Map(primaryRatings
            .measurableCategories
            .map(measurableCategory => [measurableCategory.id, measurableCategory]));

        const ratingsById = new Map(primaryRatings
            .ratingSchemeItems
            .map(ratingSchemeItem => [ratingSchemeItem.id, ratingSchemeItem]));

        const ratingRows = primaryRatings
            .measurableRatings
            .reduce((acc: RatingRow[], d) => {
                const measurable = measurablesById.get(d.measurableId);
                const category = measurable && categoriesById.get(measurable.categoryId);
                const ratingSchemeItem = ratingsById.get(d.ratingId);
                if(measurable && category && ratingSchemeItem) {
                    acc.push({
                        rating: d,
                        measurable,
                        category,
                        ratingSchemeItem
                    });
                }
                return acc;
            }, [])
            .sort((a, b) => a.category.position - b.category.position)
            .sort((a, b) => a.category.name.localeCompare(b.category.name));

        return (
            <table className="table table-condensed waltz-field-table-border" style={{width: "100%"}}>
                <tbody>
                {ratingRows.map((row, idx) => (
                    <tr key={idx}>
                        <td className="wft-label" style={{padding: "3px", width: "30%"}}>
                            {row.category?.name || "Unknown Category"}
                        </td>
                        <td style={{padding: "3px 6px", width: "70%"}}>
                            <Tooltip
                                content={<PrimaryRatingTooltipContent
                                    rating={row.rating}
                                    ratingSchemeItem={row.ratingSchemeItem}
                                    measurable={row.measurable}/>}
                                placement="left-start">
                                <div style={{display: "flex", alignItems: "center", gap: "0.5rem"}}>
                                    <RatingIndicatorCell rating={row.ratingSchemeItem} showName={false}/>
                                    <span>{row.measurable?.name || "Unknown Measurable"}</span>
                                </div>
                            </Tooltip>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>
        );
    };

    return (
        <>
            {ratingsPending && <SubSectionLoader/>}
            {primaryRatings && (
                <SubSection header="Primary Viewpoint Ratings"
                            content={content()}
                            controls={<PrimaryViewpointRatingsControls/>}/>
            )}
        </>
    )
}

export default PrimaryViewpointRatings;