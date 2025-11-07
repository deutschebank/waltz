import React from "react";
import { kindToViewState } from "../../../../common/link-utils";
import ViewLink from "../view-link/ViewLink";
import EntityLabel from "./EntityLabel";
import {EntityReference} from "../../../types/Entity";

interface EntityLinkProps {
    ref: EntityReference;
    isSecondaryLink?: boolean;
    showIcon?: boolean;
    children?: React.ReactNode;
    showTitle?: boolean
}

const EntityLink: React.FC<EntityLinkProps> = ({
    ref,
    isSecondaryLink = false,
    showIcon = true,
    children,
    showTitle = true
}) => {

    const state = ref ? kindToViewState(ref.kind) : null;

    if (!ref) {
        return null;
    }

    return (
        <ViewLink state={state} isSecondaryLink={isSecondaryLink} ctx={ref} showTitle={showTitle}>
            {children ? children : <EntityLabel ref={ref} showIcon={showIcon} />}
        </ViewLink>
    );
};

export default EntityLink;
