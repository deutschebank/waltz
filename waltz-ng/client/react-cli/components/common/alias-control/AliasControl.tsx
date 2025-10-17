import React, { useState, useEffect } from "react";
import TagInput from "../tags-input/TagsInput";
import "./AliasControl.module.scss";

// Utility imports
import { entityAliasStore } from "../../../../svelte-stores/entity-alias-store"; // Placeholder for API calls
import toasts from "../../../../svelte-stores/toast-store";
// import {displayError} from "../../../../common/error-utils";

interface ParentProps {
    parentEntityReference: any; // Change `any` to a specific type if available
    editable?: boolean; // Optional editable flag
}

enum Modes {
    VIEW = "VIEW",
    EDIT = "EDIT",
}

const AliasControl: React.FC<ParentProps> = ({
    parentEntityReference,
    editable = false,
}) => {
    const [mode, setMode] = useState<Modes>(Modes.VIEW); // View/Edit state
    const [aliases, setAliases] = useState<string[]>([]); // Aliases list
    const [fetchCallComplete, setFetchCallComplete] = useState<boolean>(false); // State for tracking fetch call completion

    // Fetch aliases on component mount if `parentEntityReference` exists
    useEffect(() => {
        const fetchAliases = async () => {
            if (parentEntityReference) {
                try {
                    const data = await entityAliasStore.fetchForEntityReference(
                        parentEntityReference
                    );
                    setAliases(data || []);
                    setFetchCallComplete(true);
                } catch (error) {
                    console.error("Failed to fetch aliases:", error);
                    // displayError("Failed to fetch aliases.");
                }
            }
        };
        fetchAliases();
    }, [parentEntityReference]);

    // Save aliases API call
    const onSave = async (updatedAliases: string[]) => {
        try {
            await entityAliasStore.updateForEntityReference(
                parentEntityReference,
                updatedAliases
            );
            setAliases(updatedAliases); // Update aliases list
            toasts.success("Updated aliases");
            setMode(Modes.VIEW); // Switch back to view mode
        } catch (error) {
            console.error("Failed to update aliases:", error);
            // displayError("Failed to update aliases.");
        }
    };

    // Cancel edit mode
    const onCancel = () => {
        setMode(Modes.VIEW);
    };

    return (
        <div className="waltz-alias-list">
            {/* Render aliases in VIEW mode */}
            {mode === Modes.VIEW && fetchCallComplete && (
                <>
                    {aliases.length > 0 ? (
                        <ul className="list-inline">
                            {aliases.map((alias, index) => (
                                <li className="tag" key={index}>
                                    {alias}
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <ul>
                            <li className="text-muted">No aliases defined</li>
                        </ul>
                    )}
                    {editable && (
                        <button
                            className="btn-skinny"
                            onClick={() => setMode(Modes.EDIT)}
                        >
                            Edit
                        </button>
                    )}
                </>
            )}

            {/* Render TagInput in EDIT mode */}
            {mode === Modes.EDIT && (
                <TagInput
                    value={aliases}
                    list={aliases} // Assuming some suggestions could come from the current aliases list
                    onSave={onSave}
                    onCancel={onCancel}
                />
            )}
        </div>
    );
};

export default AliasControl;
