/*
 * Waltz - Enterprise Architecture
 * react-component: AngularJS directive to embed React components with dynamic prop binding
 * Inspired by svelte-component.js
 */

import React from "react";
import { createRoot } from "react-dom/client";
import _ from "lodash";

function calcParamKeys(attrs) {
    return _.chain(attrs)
        .map((p, k) => ({ p, k }))
        .reject(d => d.k.startsWith("$"))
        .value();
}

function calcInitialParams(paramKeys, scope) {
    return _.chain(paramKeys)
        .map(d => Object.assign({}, d, { v: scope.$eval(d.p) }))
        .reduce((acc, d) => {
            acc[d.k] = d.v;
            return acc;
        }, {})
        .value();
}

const directive = function () {
    return {
        restrict: "E",
        link: (scope, elem, attrs) => {
            const paramKeys = calcParamKeys(attrs);
            let currentProps = calcInitialParams(paramKeys, scope);
            // Look up the React component from the scope using the 'component' attribute
            let ReactComponent = _.get(scope, attrs.component);

            let root = null;

            function doRender() {
                ReactComponent = _.get(scope, attrs.component); // re-fetch in case it changes
                if (ReactComponent) {
                    if (!root) {
                        root = createRoot(elem[0]);
                    }
                    root.render(<ReactComponent {...currentProps} />);
                }
            }

            doRender();

            // Watch each param and update React props on change
            paramKeys.forEach(({ p, k }) => {
                if (p.startsWith(".")) return;
                scope.$watch(p, (newVal) => {
                    currentProps = Object.assign({}, currentProps, { [k]: newVal });
                    doRender();
                });
            });

            // Watch for changes to the component reference itself
            if (attrs.component) {
                scope.$watch(attrs.component, () => {
                    doRender();
                });
            }

            // Cleanup on destroy
            elem.on("$destroy", () => {
                if (root) root.unmount();
            });
        }
    };
};

export default directive;
