import React from "react";
import styles from "./SubSectionLoader.module.css";

const SubSectionLoader: React.FC = () => {
    return (
        <div className={styles.subSectionLoader}>
            <div className={styles.headerSkeleton}></div>
            <div className={styles.contentSkeleton}></div>
        </div>
    );
};

export default SubSectionLoader;
