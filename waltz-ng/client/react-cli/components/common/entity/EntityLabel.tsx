import * as React from "react";
import _ from "lodash";
import EntityIcon from "./EntityIcon";

export interface EntityLabelProps {
    ref: any; // entity reference
    showIcon?: boolean;
}

const nameMap = {
    SERVER: "hostname",
    DATABASE: "databaseName"
};

export const EntityLabel = ({ ref, showIcon = true }: EntityLabelProps) => {
    const name = _.get(ref, nameMap[ref?.kind] || "name", "unknown");
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