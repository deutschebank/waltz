import React, {useState} from "react";
import {AppGroupType} from "../../types/AppGroup";
import EntityLink from "../common/entity/EntityLink";
import {mkRef} from "../../utils/mkRef";
import styles from "./Application.module.css"
import Tooltip from "../common/tooltip/Tooltip";
import Markdown from "../common/markdown/Markdown";

interface AppGroupListProps {
    appGroups: AppGroupType[];
}

const AppGroupList: React.FC<AppGroupListProps> = ({appGroups}) => {
    const [showMore, setShowMore] = useState(true);

    // sort the app groups by name (ascending)
    appGroups.sort((a, b) => a.name.localeCompare(b.name));

    return (
        <span className={styles.appGroupList}>
            {appGroups.slice(0, showMore ? 10 : appGroups.length).map((appGroup: AppGroupType) => {
                return (
                    <Tooltip key={appGroup.id} content={appGroup.description && appGroup.description.length ? <Markdown text={appGroup.description}/> : null} placement="top">
                        <div className={styles.pill}>
                            <EntityLink
                                ref={mkRef(appGroup)}
                                showTitle={false}/>
                        </div>
                    </Tooltip>
                );
            })}
            {appGroups.length > 10 && showMore && (
                <div className={styles.showMore} onClick={() => setShowMore(false)}>
                    [show all {appGroups.length}]
                </div>
            )}
            {appGroups.length > 10 && !showMore && (
                <div className={styles.showMore} onClick={() => setShowMore(true)}>
                    [show less]
                </div>
            )}
        </span>
    );
}

export default AppGroupList;