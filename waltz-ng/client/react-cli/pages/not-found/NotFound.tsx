import React from "react";
import styles from "./NotFound.module.css";
import Icon from "../../components/common/Icon";

const NotFound = () => {
  return (
    <div className={styles.content}>
      <div className={styles.contentRow}>
        <Icon name="triangle-exclamation" />
        <div className="text-muted">The page you are looking for does not exist.</div>
      </div>
    </div>
  );
};

export default NotFound;
