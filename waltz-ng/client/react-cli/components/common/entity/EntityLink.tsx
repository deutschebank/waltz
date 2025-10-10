import React from "react";
import _ from "lodash";
import { kindToViewState } from "../../../../common/link-utils";
import ViewLink from "../view-link/ViewLink";
import EntityLabel from "./EntityLabel";

interface EntityLinkProps {
    ref: {
        id: number;
        kind: string;
        name?: string;
    } | null;
    isSecondaryLink?: boolean;
    showIcon?: boolean;
    children?: React.ReactNode;
}

const EntityLink: React.FC<EntityLinkProps> = ({
    ref,
    isSecondaryLink = false,
    showIcon = true,
    children
}) => {

    const state = ref ? kindToViewState(ref.kind) : null;

    if (!ref) {
        return null;
    }

    return (
        <ViewLink state={state} isSecondaryLink={isSecondaryLink} ctx={ref}>
            {children ? children : <EntityLabel ref={ref} showIcon={showIcon} />}
        </ViewLink>
    );
};

export default EntityLink;
