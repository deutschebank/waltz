import React from "react";
import Markdown from "../markdown/Markdown";
import Icon from "../Icon";
import {StaticPanelType} from "../../../types/StaticPanel";

interface StaticPanelProps {
    panel: StaticPanelType
    showTitle?: boolean
    showIcon?: boolean
}

export default function StaticPanel({panel, showTitle = true, showIcon = true}: StaticPanelProps) {
    return (
        <div style={{marginBottom: "1em"}}>
            <h4>
                {showTitle && showIcon && <Icon name={panel.icon}/>}
                {showTitle && panel.title}
            </h4>
            <span>
                {showIcon && !showTitle && <Icon name={panel.icon}/>}
                <span>
                    <Markdown text={panel.content}
                    inline={true}/>
                </span>
            </span>
        </div>
    );
}