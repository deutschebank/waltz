import React, { useState } from "react";
import TagInput from "../tags-input/TagsInput";
import "./AliasControl.module.scss";
// Utility imports
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
    getEntityReference,
    updateEntityReference,
} from "../../../api/entity-alias";
import { displayError } from "../../../../common/error-utils";
import { EntityReference } from "../../../types/Entity";

// Types
type ParentProps = {
    parentEntityReference: Pick<EntityReference, "kind" | "id">;
    editable?: boolean;
};

// ENUMS
enum Modes {
    VIEW = "VIEW",
    EDIT = "EDIT",
}

const AliasControl: React.FC<ParentProps> = ({
    parentEntityReference,
    editable = false,
}) => {
    const [mode, setMode] = useState<Modes>(Modes.VIEW); // View/Edit state
    const queryClient = useQueryClient(); // Required to update the query data

    // React Query for fetching the aliases
    const { isPending, data: aliases = [] } = useQuery(
        getEntityReference(parentEntityReference)
    );

    // Update aliases
    const mutation = useMutation({
        mutationFn: (newAliases: string[]) => {
            // React Query for update new aliases
            const { queryFn } = updateEntityReference(
                parentEntityReference,
                newAliases
            );
            return queryFn();
        },
        onSuccess: (updatedAliases) => {
            // update the cache with new data
            queryClient.setQueryData(
                ["entity-alias", parentEntityReference],
                updatedAliases
            );
            // toast.success("Updated aliases successfully");
            setMode(Modes.VIEW); // Switch back to view mode
        },
        onError: (error) => {
            displayError("Failed to update aliases", error);
        },
    });

    // on save call the mutation with new aliases
    const onSave = (newAliases: string[]) => {
        mutation.mutate(newAliases);
    };

    // Cancel edit mode
    const onCancel = () => {
        setMode(Modes.VIEW);
    };

    return (
        <div className="waltz-alias-list">
            {/* Render aliases in VIEW mode */}
            {mode === Modes.VIEW && !isPending && (
                <>
                    {aliases.length > 0 ? (
                        <ul className="list-inline">
                            {aliases.map((alias: string, index: number) => (
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
