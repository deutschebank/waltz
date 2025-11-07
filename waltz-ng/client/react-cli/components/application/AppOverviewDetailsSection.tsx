import React from "react";
import {ApplicationType} from "../../types/Application";
import AppOverviewDetailsRow from "./AppOverviewDetailsRow";
import AliasControl from "../common/alias-control/AliasControl";
import {mkRef} from "../../utils/mkRef";
import TagsInput from "../common/tags-input/TagsInput";
import DateTime from "../common/DateTime";
import Markdown from "../common/markdown/Markdown";
import {useQuery} from "@tanstack/react-query";
import {appGroupApi} from "../../api/app-group";
import AppGroupList from "./AppGroupList";
import {orgUnitApi} from "../../api/org-unit";
import EntityLink from "../common/entity/EntityLink";
import {RatingIndicatorCell} from "../common/assessment-rating/RatingIndicatorCell";
import {RatingSchemeItemType} from "../../types/Rating";
import {useDefaultRatingScheme} from "../../hooks/useDefaultRatingScheme";

interface AppOverviewDetailsSectionProps {
    application: ApplicationType
}

export default function AppOverviewDetailsSection({application}: AppOverviewDetailsSectionProps) {
    // placeholder function to be replaced by the toDisplayNameService
    const toDisplayName = (inputString?: string) => {
        if (!inputString) {
            return "";
        }
        return inputString
            .split(/[\s_]+/) // Split by spaces or underscores
            .map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
            .join(" ");
    };

    const ratingItem: RatingSchemeItemType = useDefaultRatingScheme(application.overallRating, "investment")

    const {isPending: appGroupsPending, data: appGroups = []} = useQuery(appGroupApi.findRelatedByEntityRef(mkRef(application)));
    const {isPending: orgUnitPending, data: orgUnit} = useQuery(orgUnitApi.getById(application.organisationalUnitId));

    return (
        <>
            {/*<waltz-asset-code-explorer application="$ctrl.app">*/}
            {/*</waltz-asset-code-explorer>*/}

            {/*toDisplayName*/}

            {/* Overall Rating */}
            {/*<waltz-rating-indicator-cell*/}
            {/*    rating="$ctrl.app.overallRating | useDefaultRatingScheme:'investment' "*/}
            {/*    show-description-popup="true"*/}
            {/*    show-name="true">*/}
            {/*</waltz-rating-indicator-cell>*/}

            {/* Organisational Unit Name */}

            {/* Complexity Rating */}
            <AppOverviewDetailsRow label="Name"
                                   value={application.name}
                                   label2="Asset Code"
                                   value2={application.assetCode}/>

            <AppOverviewDetailsRow label="Lifecycle Phase"
                                   value={toDisplayName(application.lifecyclePhase)}
                                   label2="Type"
                                   value2={toDisplayName(application.applicationKind)}/>

            <AppOverviewDetailsRow label="Owning Org Unit"
                                   value={orgUnit && <EntityLink ref={mkRef(orgUnit)} showIcon={false}/>}
                                   label2="Overall Rating"
                                   value2={orgUnit && <RatingIndicatorCell rating={ratingItem}/>}/>

            <AppOverviewDetailsRow label="Aliases"
                                   value={<AliasControl parentEntityReference={mkRef(application)}
                                                        editable={true}/>}
                                   label2="Tags"
                                   value2={<TagsInput/>}/>

            <AppOverviewDetailsRow label="Complexity Rating"
                                   value={0.37}
                                   label2="Business Criticality"
                                   value2={toDisplayName(application.businessCriticality)}/>

            <AppOverviewDetailsRow label="Planned Retirement"
                                   value={application.plannedRetirementDate
                                       ? <DateTime dateTime={application?.plannedRetirementDate}/> : "-"}
                                   label2="Actual Retirement"
                                   value2={application.actualRetirementDate ?
                                       <DateTime dateTime={application.actualRetirementDate}/> : "-"}/>

            <AppOverviewDetailsRow label="Commission"
                                   value={application.commissionDate
                                       ? <DateTime dateTime={application?.commissionDate}/> : "-"}
                                   label2="Provenance"
                                   value2={application.provenance ?? "-"}/>

            <AppOverviewDetailsRow label="Description"
                                   value={<Markdown text={application.description}/>}/>

            <AppOverviewDetailsRow label="Application Groups"
                                   value={<AppGroupList appGroups={appGroups}/>}/>
        </>
    );
}