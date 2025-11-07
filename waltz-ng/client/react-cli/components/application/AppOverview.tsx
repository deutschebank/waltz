import React from "react";
import {ApplicationType} from "../../types/Application";
import Button from "../common/button/Button";
import AppOverviewDetailsSection from "./AppOverviewDetailsSection";
import AppOverviewSubSections from "./AppOverviewSubSections";
import reduxStore from "../../../redux-store";
import {navigate} from "../../../redux-slices/page-nav-slice";

interface AppOverviewProps {
    application: ApplicationType
}

const AppOverview: React.FC<AppOverviewProps> = ({application}) => {
    const onClickEdit = () => {
        reduxStore.dispatch(navigate({
            state: "main.app.edit",
            params: {
                id: application.id
            }
        }));
    }

    return (
        <div>
            <div className="waltz-section-actions">
                <Button label="Edit" onClick={onClickEdit} className="btn btn-xs btn-primary"/>
            </div>
            <div className="waltz-display-section">
                <div className="row">
                    <div className="col-md-7">
                        <AppOverviewDetailsSection application={application}/>
                    </div>
                    <div className="col-md-5">
                        <AppOverviewSubSections application={application}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AppOverview;