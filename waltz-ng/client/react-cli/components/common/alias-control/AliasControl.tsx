import React, { useState } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getEntityReference, updateEntityReference } from "../../../api/entity-alias";
import { displayError } from "../../../../common/error-utils";
import { EntityReference } from "../../../types/Entity";
import Button from "../button/Button";
import TagInput from "../tags-input/TagsInput";
import styles from "./AliasControl.module.scss";
import { useToasts } from "../../../context/toast/ToastContext";
import { NotificationTypeEnum } from "../../../enums/Notification";
import { VisualStateModes } from "../../../enums/VisualState";

// Types
type ParentProps = {
  parentEntityReference: EntityReference;
  editable?: boolean;
};

const AliasControl: React.FC<ParentProps> = ({
  parentEntityReference,
  editable = false,
}) => {
  const [mode, setMode] = useState<VisualStateModes>(VisualStateModes.VIEW); // View/Edit state
  const queryClient = useQueryClient(); // Required to update the query data
  const { addToast } = useToasts();

  // React Query for fetching the aliases
  const { isPending, data: aliases = [] } = useQuery(
    getEntityReference(parentEntityReference)
  );

  // Update aliases
  const mutation = useMutation({
    mutationFn: (newAliases: string[]) => {
      // React Query for update new aliases
      const { queryFn } = updateEntityReference(parentEntityReference, newAliases);
      return queryFn();
    },
    onSuccess: (updatedAliases) => {
      // update the cache with new data
      queryClient.setQueryData(["entity-alias", parentEntityReference], updatedAliases);
      addToast({
        type: NotificationTypeEnum.SUCCESS,
        message: "Updated aliases successfully",
      });
      setMode(VisualStateModes.VIEW);
    },
    onError: (error) => {
      displayError("Failed to update aliases", error);
      console.error("Failed to update aliases", error);
    },
  });

  const onSave = (newAliases: string[]) => {
    mutation.mutate(newAliases);
  };

  const onCancel = () => {
    setMode(VisualStateModes.VIEW);
  };

  return (
    <div className="waltz-alias-list">
      {/* Render aliases in VIEW mode */}
      {mode === VisualStateModes.VIEW && !isPending && (
        <ul className="list-inline">
          {aliases.length > 0 ? (
            aliases.map((alias: string, index: number) => (
              <li className={styles.tag} key={index}>
                {alias}
              </li>
            ))
          ) : (
            <li className="text-muted" data-testid="no-aliases">
              No aliases defined
            </li>
          )}
          {editable && (
            <li>
              <Button className="btn-skinny" onClick={() => setMode(VisualStateModes.EDIT)}>
                Edit
              </Button>
            </li>
          )}
        </ul>
      )}

      {/* Render TagInput in EDIT mode */}
      {mode === VisualStateModes.EDIT && (
        <TagInput
          value={aliases}
          list={aliases}
          onSave={onSave}
          onCancel={onCancel}
        />
      )}
    </div>
  );
};

export default AliasControl;
