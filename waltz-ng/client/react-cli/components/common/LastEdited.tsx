import React from "react";
import DateTime from "./DateTime";

interface LastEditedProps {
  entity: {
    lastUpdatedBy: string;
    lastUpdatedAt: string | Date;
  } | null;
  showLabel?: boolean;
}

const LastEdited: React.FC<LastEditedProps> = ({entity, showLabel = false}) => {
  if (!entity) {
    return null;
  }

  return (
    <span>
      {showLabel && <span className="text-muted">Last Updated: </span>}
      {entity.lastUpdatedBy},
      <DateTime
        relative={true}
        formatStr="yyyy-MM-DD HH:mm:ss"
        dateTime={entity.lastUpdatedAt}
      />
    </span>
  );
};

export default LastEdited;
