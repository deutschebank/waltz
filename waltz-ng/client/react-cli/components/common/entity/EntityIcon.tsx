import * as React from "react";
import _ from "lodash";
import {entity} from "../../../../common/services/enums/entity";
import Icon from "../Icon";

export interface EntityIconProps {
    kind: string;
    showName?: boolean;
}

export const EntityIcon = ({ kind, showName = false }: EntityIconProps) => {
    const iconName = _.get(entity, [kind, "icon"], "circle-o");
    const label = _.get(entity, [kind, "name"], "");

    return (
        <>
            <Icon name={iconName} />
            {showName && <span>{label}</span>}
        </>
    );
};

export default EntityIcon;