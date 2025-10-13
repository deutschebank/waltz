import * as React from "react";
import _ from "lodash";
import EntityIcon from "./EntityIcon";
import {EntityReference} from "../../../types/Entity";
import {IsRemovedProvider} from "../../../types/Providers";

export interface EntityLabelProps {
    ref: EntityReference & Partial<IsRemovedProvider>;
    showIcon?: boolean;
}

const nameMap = {
    SERVER: "hostname",
    DATABASE: "databaseName"
};

export const EntityLabel = ({ ref, showIcon = true }: EntityLabelProps) => {
    const name = ref[nameMap[ref.kind]] || ref?.name || "unknown";
    const isRemoved = ref?.entityLifecycleStatus === 'REMOVED' || ref?.isRemoved;

    const classes = ["force-wrap"];
    if (isRemoved) {
        classes.push("removed");
    }

    return (
        <>
            {showIcon && <EntityIcon kind={ref?.kind} />}
            <span className={classes.join(" ")}>
                {name}
            </span>
        </>
    );
};

export default EntityLabel;