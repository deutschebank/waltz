import React, { useMemo } from "react";
import _ from "lodash";
import styles from "./ViewLink.module.scss";

interface ViewLinkProps {
    state: string;
    title?: string;
    ctx?: any;
    isSecondaryLink?: boolean;
    children: React.ReactNode;
    showTitle?: boolean;
}

const routes = {
    "main": {
        path: () => "/",
        title: "Home page"
    },
    "main.home": {
        path: () => "home",
        title: "Home page"
    },
    "main.system.list": {
        path: () => "system/list",
        title: "System Administration"
    },
    "main.actor.view": {
        path: (ctx: { id: any; }) => `actor/${ctx.id}`,
        title: "Actor View"
    },
    "main.actors.list": {
        path: () => "actor/list",
        title: "Actor List"
    },
    "main.aggregate-overlay-diagram.instance-view": {
        path: (ctx: { id: any; }) => `aggregate-overlay-diagram/instance/${ctx.id}`,
        title: "Aggregate Overlay Diagram Instance View"
    },
    "main.aggregate-overlay-diagram.list": {
        path: () => `aggregate-overlay-diagram/list`,
        title: "Aggregate Overlay Diagram List View"
    },
    "main.app.view": {
        path: (ctx: { id: any; }) => `application/${ctx.id}`,
        title: "Application View"
    },
    "main.app-group.view": {
        path: (ctx: { id: any; }) => `app-group/${ctx.id}`,
        title: "Application Group View"
    },
    "main.change-initiative.view": {
        path: (ctx: { id: any; }) => `change-initiative/${ctx.id}`,
        title: "Change Initiative View"
    },
    "main.data-type.list" : {
        path: () => `data-types`,
        title: "DataTypes"
    },
    "main.data-type.view": {
        path: (ctx: { id: any; }) => `data-types/${ctx.id}`,
        title: "DataType View"
    },
    "main.flow-diagram.view": {
        path: (ctx: { id: any; }) => `flow-diagram/${ctx.id}`,
        title: "Flow Diagram View"
    },
    "main.end-user-application.view": {
        path: (ctx: { id: any; }) => `end-user-application/id/${ctx.id}`,
        title: "End User Application View"
    },
    "main.flow-classification-rule.view": {
        path: (ctx: { id: any; }) => `flow-classification-rule/${ctx.id}`,
        title: "Flow Classification Rule View"
    },
    "main.legal-entity.view": {
        path: (ctx: { id: any; }) => `legal-entity/${ctx.id}`,
        title: "Legal Entity View"
    },
    "main.legal-entity-relationship.view": {
        path: (ctx: { id: any; }) => `legal-entity-relationship/${ctx.id}`,
        title: "Legal Entity Relationship View"
    },
    "main.legal-entity-relationship-kind.view": {
        path: (ctx: { id: any; }) => `legal-entity-relationship-kind/${ctx.id}`,
        title: "Legal Entity Relationship Kind View"
    },
    "main.legal-entity-relationship-kind.list": {
        path: () => `legal-entity-relationship-kind/list`,
        title: "Legal Entity Relationship Kind List"
    },
    "main.logical-flow.view": {
        path: (ctx: { id: any; }) => `logical-flow/${ctx.id}`,
        title: "Logical Flow View"
    },
    "main.system.measurable-category.edit": {
        path: (ctx: { id: any; }) => `system/measurable-category/id/${ctx.id}/edit`,
        title: "Measurable Category Edit"
    },
    "main.system.measurable-category.create": {
        path: () => `system/measurable-category/create`,
        title: "Measurable Category Create"
    },
    "main.system.measurable-category.list": {
        path: () => `system/measurable-category/list`,
        title: "Measurable Categories"
    },
    "main.measurable.view": {
        path: (ctx: { id: any; }) => `measurable/${ctx.id}`,
        title: "Measurable View"
    },
    "main.measurable-category.list": {
        path: (ctx: { id: any; }) => `measurable-category/${ctx.id}`,
        title: "Measurable View"
    },
    "main.org-unit.view": {
        path: (ctx: { id: any; }) => `org-units/${ctx.id}`,
        title: "Org Unit View"
    },
    "main.person.id": {
        path: (ctx: { id: any; }) => `person/id/${ctx.id}`,
        title: "Person View"
    },
    "main.physical-flow.view": {
        path: (ctx: any) => `physical-flow/${ctx.id}`,
        title: "Physical Flow View"
    },
    "main.physical-specification.view": {
        path: (ctx: { id: any; }) => `physical-specification/${ctx.id}`,
        title: "Physical Specification View"
    },
    "main.process-diagram.view": {
        path: (ctx: { id: any; }) => `process-diagram/${ctx.id}`,
        title: "Process Diagram View"
    },
    "main.server.view": {
        path: (ctx: { id: any; }) => `server/${ctx.id}`,
        title: "Server View"
    },
    "main.system.static-diagrams": {
        path: () => `system/static-diagrams`,
        title: "Static Diagrams"
    },
    "main.system.static-diagram": {
        path: (ctx: { id: any; }) => `system/static-diagrams/${ctx.id}`,
        title: ""
    },
    "main.system.analytics-dashboard": {
        path: () => "system/analytics-dashboard",
        title: "Analytics Dashboard"
    },
    "main.survey.instance.view": {
        path: (ctx: { id: any; }) => `survey/instance/${ctx.id}/response/view`,
        title: "Survey View"
    },
    "main.survey.instance.edit": {
        path: (ctx: { id: any; }) => `survey/instance/${ctx.id}/response/edit`,
        title: "Survey Edit"
    },
    "main.involvement-kind.list": {
        path: () => `involvement-kind/list`,
        title: "Involvement Kind List"
    },
    "main.involvement-kind.view": {
        path: (ctx: { id: any; }) => `involvement-kind/${ctx.id}`,
        title: "Involvement Kind View"
    },
    "main.survey.template.list": {
        path: () => `survey/template/list`,
        title: "Survey Templates"
    },
    "main.role.list": {
        path: () => `role/list`,
        title: "Role List"
    },
    "main.role.view": {
        path: (ctx: { id: any; }) => `role/view/${ctx.id}`,
        title: "Role View"
    }
};

const ViewLink: React.FC<ViewLinkProps> = ({
    state,
    title: titleProp,
    ctx = {},
    isSecondaryLink = false,
    children,
    showTitle = true
}) => {

    const path = useMemo(() => {
        const pathFn = _.get(routes, [state, "path"], () => "");
        return pathFn(ctx);
    }, [state, ctx]);

    const title = useMemo(() => {
        return titleProp || _.get(routes, [state, "title"], state);
    }, [titleProp, state]);

    if (path) {
        return (
            <a href={path}
               className={isSecondaryLink ? styles.secondaryLink : ""}
               title={showTitle ? title : ""}>
                {children}
            </a>
        );
    } else {
        return <span>{children}</span>;
    }
};

export default ViewLink;