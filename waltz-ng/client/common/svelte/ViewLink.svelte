<script>
    import _ from "lodash";

    export let state;
    export let title = null;
    export let ctx = {};
    export let isSecondaryLink = false;

    let path;

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
            path: ctx => `actor/${ctx.id}`,
            title: "Actor View"
        },
        "main.actor.list": {
            path: () => "actor/list",
            title: "Actor List"
        },
        "main.aggregate-overlay-diagram.instance-view": {
            path: ctx => `aggregate-overlay-diagram/instance/${ctx.id}`,
            title: "Aggregate Overlay Diagram Instance View"
        },
        "main.aggregate-overlay-diagram.list": {
            path: () => `aggregate-overlay-diagram/list`,
            title: "Aggregate Overlay Diagram List View"
        },
        "main.app.view": {
            path: ctx => `application/${ctx.id}`,
            title: "Application View"
        },
        "main.app-group.view": {
            path: ctx => `app-group/${ctx.id}`,
            title: "Application Group View"
        },
        "main.change-initiative.view": {
            path: ctx => `change-initiative/${ctx.id}`,
            title: "Change Initiative View"
        },
        "main.data-type.list" : {
            path: ctx => `data-types`,
            title: "DataTypes"
        },
        "main.data-type.view": {
            path: ctx => `data-types/${ctx.id}`,
            title: "DataType View"
        },
        "main.flow-diagram.view": {
            path: ctx => `flow-diagram/${ctx.id}`,
            title: "Flow Diagram View"
        },
        "main.end-user-application.view": {
            path: ctx => `end-user-application/id/${ctx.id}`,
            title: "End User Application View"
        },
        "main.flow-classification-rule.view": {
            path: ctx => `flow-classification-rule/${ctx.id}`,
            title: "Flow Classification Rule View"
        },
        "main.legal-entity.view": {
            path: ctx => `legal-entity/${ctx.id}`,
            title: "Legal Entity View"
        },
        "main.legal-entity-relationship.view": {
            path: ctx => `legal-entity-relationship/${ctx.id}`,
            title: "Legal Entity Relationship View"
        },
        "main.legal-entity-relationship-kind.view": {
            path: ctx => `legal-entity-relationship-kind/${ctx.id}`,
            title: "Legal Entity Relationship Kind View"
        },
        "main.legal-entity-relationship-kind.list": {
            path: () => `legal-entity-relationship-kind/list`,
            title: "Legal Entity Relationship Kind List"
        },
        "main.logical-flow.view": {
            path: ctx => `logical-flow/${ctx.id}`,
            title: "Logical Flow View"
        },
        "main.system.measurable-category.edit": {
            path: ctx => `system/measurable-category/id/${ctx.id}/edit`,
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
            path: ctx => `measurable/${ctx.id}`,
            title: "Measurable View"
        },
        "main.measurable-category.list": {
            path: ctx => `measurable-category/${ctx.id}`,
            title: "Measurable View"
        },
        "main.org-unit.view": {
            path: ctx => `org-units/${ctx.id}`,
            title: "Org Unit View"
        },
        "main.person.id": {
            path: ctx => `person/id/${ctx.id}`,
            title: "Person View"
        },
        "main.physical-flow.view": {
            path: ctx => `physical-flow/${ctx.id}`,
            title: "Physical Flow View"
        },
        "main.physical-specification.view": {
            path: ctx => `physical-specification/${ctx.id}`,
            title: "Physical Specification View"
        },
        "main.process-diagram.view": {
            path: ctx => `process-diagram/${ctx.id}`,
            title: "Process Diagram View"
        },
        "main.server.view": {
            path: ctx => `server/${ctx.id}`,
            title: "Server View"
        },
        "main.system.static-diagrams": {
            path: () => `system/static-diagrams`,
            title: "Static Diagrams"
        },
        "main.system.static-diagram": {
            path: () => `system/static-diagrams/${ctx.id}`,
            title: ""
        },
        "main.system.analytics-dashboard": {
            path: () => "system/analytics-dashboard",
            title: "Analytics Dashboard"
        },
        "main.survey.instance.view": {
            path: ctx => `survey/instance/${ctx.id}/response/view`,
            title: "Survey View"
        },
        "main.survey.instance.edit": {
            path: ctx => `survey/instance/${ctx.id}/response/edit`,
            title: "Survey Edit"
        },
        "main.involvement-kind.list": {
            path: () => `involvement-kind/list`,
            title: "Involvement Kind List"
        },
        "main.involvement-kind.view": {
            path: ctx => `involvement-kind/${ctx.id}`,
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
            path: (ctx) => `role/view/${ctx.id}`,
            title: "Role View"
        }
    };

    $: {
        path = _.get(routes, [state, "path"], () => "")(ctx);
        title = title || _.get(routes, [state, "title"], state);
    }

</script>

{#if path}
    <a href={path}
       class:secondary-link={isSecondaryLink}
       {title}>
        <slot></slot>
    </a>
{:else }
    <span>
        <slot/>
    </span>
{/if}


<style type="text/scss">
    @import '../../../style/variables';

    .secondary-link {
        color: $waltz-font-color;
        font-weight: bold;

        &:hover {
            color: $waltz-link-color;
        }
    }
</style>