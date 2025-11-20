import * as React from "react";
import {entity} from "../../../constants/entity";
import Icon from "../Icon";
import {EntityKind} from "../../../types/Entity";

export interface EntityIconProps {
    kind: EntityKind;
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