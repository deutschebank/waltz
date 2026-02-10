import React from "react";
import Icon from "../Icon";
import styles from "./MiniActions.module.scss";

export interface Action {
  name?: string;
  icon?: string;
  handleAction: (ctx: any) => void;
  description?: string;
}

interface MiniActionsProps {
  actions: Action[];
  ctx: any;
}

const MiniActions: React.FC<MiniActionsProps> = ({actions = [], ctx}) => {
  if (!actions || actions.length === 0) {
    return null;
  }

  return (
    <ul className={styles.miniActions}>
      {actions.map((action, index) => (
        <li key={index} className="clickable" title={action.description}>
          <button className="btn btn-skinny" onClick={() => action.handleAction(ctx)}>
            {action.icon && <Icon name={action.icon} />}
            {action.name}
          </button>
        </li>
      ))}
    </ul>
  );
};

export default MiniActions;
