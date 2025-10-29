import React from "react";
import StaticPanel from "./StaticPanel";
import {staticPanelApi} from "../../../api/static-panel";
import {useQuery} from "@tanstack/react-query";
import Loader from "../loader/Loader";
import Toast from "../toast/Toast";

interface StaticPanelsProps {
    groupKey: string
    showTitle?: boolean
    showIcon?: boolean
}

export default function StaticPanels({groupKey, showTitle = true, showIcon = true}: StaticPanelsProps) {
    const {isPending,data, isError, error} = useQuery(staticPanelApi.findByGroupKey(groupKey));

    return (
        <>
            {isPending && <Loader/>}
            {data && data.map(panel => <StaticPanel key={panel.id} panel={panel} showTitle={showTitle} showIcon={showIcon}/>)}
            {isError && <Toast type={"ERROR"} message={error?.message || "Error loading static panels"}/>}
            {!isPending && !isError && data?.length === 0 && <Toast type={"ERROR"} message={`Static panels for group, ${groupKey} were not found.`}/>}
        </>
    );
}