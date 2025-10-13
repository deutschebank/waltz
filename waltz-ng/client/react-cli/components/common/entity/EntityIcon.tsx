import * as React from "react";
import {entity} from "../../../constants/entity";
import Icon from "../Icon";

export interface EntityIconProps {
    kind: string;
    showName?: boolean;
}

export const EntityIcon = ({ kind, showName = false }: EntityIconProps) => {
    const iconName = entity[kind]?.icon || "circle-o";
    const label = entity[kind]?.name || "";

    return (
        <>
            <Icon name={iconName} />
            {showName && <span>{label}</span>}
        </>
    );
};

export default EntityIcon;