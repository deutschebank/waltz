import React from "react";
import styles from "./BreadCrumbs.module.scss";
import ViewLink from "../view-link/ViewLink";
import {BreadCrumbsConfig} from "../../../types/BreadCrumbs";

interface BreadCrumbsProps {
    crumbs: BreadCrumbsConfig[]
}

export default function BreadCrumbs({crumbs}: BreadCrumbsProps) {
    return (
        <div>
            <ol className={styles.waltzBreadcrumbs}>
                {crumbs
                    .map((crumb, idx) => {
                        return <li key={idx}>
                            {crumb.href && <a href={crumb.href}>{crumb?.text}</a>}
                            {!crumb.href && crumb.state && <ViewLink state={crumb.state}>{crumb?.text}</ViewLink>}
                            {!crumb.href && !crumb.state && <div>{crumb?.text}</div>}
                        </li>
                    })
                }
            </ol>
        </div>
    );
}