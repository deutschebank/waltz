import React, { useState } from "react";
import TagInput from "../tags-input/TagsInput";
import "./AliasControl.module.scss";
// Utility imports
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { aliasQuery, updateAliases } from "../../../api/entity-alias";

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
    const queryClient = useQueryClient(); // Required to update the query data

    const { isPending, data: aliases = [] } = useQuery(
        aliasQuery(parentEntityReference)
    );

    // Update aliases
    const mutation = useMutation({
        mutationFn: (newAliases: string[]) => {
            const { queryFn } = updateAliases(
                parentEntityReference,
                newAliases
            );
            return queryFn();
        },
        onSuccess: (udatedAliases) => {
            // toast.success("Updated aliases successfully");
            queryClient.setQueryData(
                ["entity-alias", parentEntityReference],
                udatedAliases
            ); // Refetch the aliases
            setMode(Modes.VIEW); // Switch back to view mode
        },
        onError: (error) => {
            console.error("Failed to update aliases:", error);
            // toast.error(`Failed to update aliases: ${error}`);
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
