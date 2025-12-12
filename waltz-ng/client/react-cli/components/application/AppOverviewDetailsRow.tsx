import React from "react";

interface AppOverviewDetailsRowProps {
    label: string,
    value: string | React.ReactNode,
    label2?: string,
    value2?: string | React.ReactNode
}

export default function AppOverviewDetailsRow({label, value, label2, value2}: AppOverviewDetailsRowProps) {
    if(label2 && value2) {
        return (
            <div className="row row-no-gutters">
                <div className="col-sm-2 waltz-display-field-label">
                    {label}
                </div>
                <div className="col-sm-4 waltz-display-field-value">
                    {typeof value === "string" &&
                        <span>{value}</span>}
                    {typeof value !== "string" && value}
                </div>

                <div className="col-sm-2 waltz-display-field-label">
                    {label2}
                </div>
                <div className="col-sm-4 waltz-display-field-value">
                    {value2 && typeof value2 === "string" &&
                        <span>{value2}</span>}
                    {value2 && typeof value2 !== "string" && value2}
                </div>
            </div>
        )
    } else {
        return (
            <div className="row row-no-gutters">
                <div className="col-sm-2 waltz-display-field-label">
                    {label}
                </div>
                <div className="col-sm-10 waltz-display-field-value">
                    {typeof value === "string" &&
                        <span>{value}</span>}
                    {typeof value !== "string" && value}
                </div>
            </div>
        )
    }
}