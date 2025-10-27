
import React, { useState, useEffect } from "react";
import { truncateMiddle } from "../../../../common/string-utils";
import Icon from "../Icon";
import styles from "./PageHeader.module.scss";
import {BreadCrumbsConfig} from "../../../types/BreadCrumbs";
import BreadCrumbs from "../breadcrumbs/BreadCrumbs";

interface PageHeaderProps {
    icon?: string;
    name: string;
    small?: string;
    breadcrumbs?: BreadCrumbsConfig[];
    actions?: React.ReactNode;
    summary?: React.ReactNode;
}

const pageHeaderDefaultOffset = 60;

const PageHeader: React.FC<PageHeaderProps> = ({
    icon = "dot-circle-o",
    name,
    small = "",
    breadcrumbs,
    actions,
    summary
}) => {
    const [pageY, setPageY] = useState(0);

    const handleScroll = () => {
        setPageY(window.scrollY);
    };

    useEffect(() => {
        window.addEventListener("scroll", handleScroll);
        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, []);

    const stickyVisible = pageY > pageHeaderDefaultOffset;

    const pageTitle = name
        ? `${name} - Waltz`
        : "Waltz";

    useEffect(() => {
        document.title = pageTitle;
    }, [pageTitle]);


    return (
        <div className={styles.header}>
            <div className={styles.headerFree}>
                {breadcrumbs && <BreadCrumbs crumbs={breadcrumbs}/>}
                <h2>
                    <Icon name={icon} />
                    <span title={name}>{truncateMiddle(name, 70)}</span>
                    <small>{small}</small>
                    {actions}
                </h2>
            </div>

            <div className={`${styles.headerSticky} ${stickyVisible ? styles.stickyVisible : ""}`}>
                <button className="btn-skinny" onClick={() => window.scrollTo(0, 0)}>
                    <h2 className="clickable">
                        <Icon name={icon} />
                        <span>{name}</span>
                        <small>{small}</small>
                    </h2>
                </button>
            </div>

            {summary && (
                <div className="waltz-page-summary waltz-page-summary-attach">
                    {summary}
                </div>
            )}
        </div>
    );
};

export default PageHeader;
