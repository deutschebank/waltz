// src/components/loader/Loader.tsx
import React from "react";
import styles from "./Loader.module.scss";

const Loader: React.FC = () => {
    return (
        <div className={styles.loaderContainer}>
            <div className={styles.spinner}></div>
            <p>Loading, please wait...</p>
        </div>
    );
};

export default Loader;
