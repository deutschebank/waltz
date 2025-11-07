import * as React from "react";
import EntityIcon from "./EntityIcon";
import {EntityReference} from "../../../types/Entity";
import {DatabaseNameProvider, HostnameProvider, IsRemovedProvider} from "../../../types/Providers";
import styles from "./EntityLabel.module.css";

export interface EntityLabelProps {
    ref: EntityReference & Partial<IsRemovedProvider> & Partial<HostnameProvider> & Partial<DatabaseNameProvider>;
    showIcon?: boolean;
}

const nameMap = {
    SERVER: "hostname",
    DATABASE: "databaseName"
};

export const EntityLabel = ({ ref, showIcon = true }: EntityLabelProps) => {
    const propertyName = nameMap[ref.kind as keyof typeof nameMap];
    const specialName = propertyName ?
        propertyName === nameMap.SERVER
            ? ref?.hostname : ref?.databaseName
        : undefined;
    const name = specialName || ref?.name || "unknown";
    const isRemoved = ref?.entityLifecycleStatus === 'REMOVED' || ref?.isRemoved;

    const classes = ["force-wrap"];
    if (isRemoved) {
        classes.push("removed");
    }

    return (
        <>
            <div className={styles.entityLabel}>
                {showIcon && <EntityIcon kind={ref?.kind}/>}
                <span className={classes.join(" ")}>
                    {name}
                </span>
            </div>
        </>
    );
};

export default EntityLabel;