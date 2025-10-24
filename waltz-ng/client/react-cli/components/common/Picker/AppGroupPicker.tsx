import React, { useCallback } from "react";
import Icon from "../Icon";
import EntitySearchSelector from "../entity-search-selector/EntitySearchSelector";

interface Props {
    onSelect?: (detail: any) => void;
    selectionFilter?: (d: any) => boolean;
}

const AppGroupPicker: React.FC<Props> = ({
    onSelect = () => console.log("Selecting app group"),
    selectionFilter = () => true,
}) => {
    // Function to handle group selection
    const onSelectGroup = useCallback(
        (e: any) => {
            if (!e?.detail) {
                return;
            }
            onSelect(e.detail);
        },
        [onSelect]
    );

    // Function to manage app group selection filter
    const appGroupSelectionFilter = useCallback(
        (d: any) => {
            return d === null ? true : selectionFilter(d);
        },
        [selectionFilter]
    );

    return (
        <div>
            <div className="help-block small">
                <Icon name="info-circle" />
                Select an app group using the search below.
            </div>
            <br />
            <EntitySearchSelector
                onSelect={onSelectGroup}
                placeholder="Search for app group"
                entityKinds={["APP_GROUP"]}
                selectionFilter={appGroupSelectionFilter}
            />
        </div>
    );
};

export default AppGroupPicker;
