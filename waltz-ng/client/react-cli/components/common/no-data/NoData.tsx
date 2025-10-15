import React from 'react';
import styles from './NoData.module.css';

interface NoDataProps {
    type?: 'dflt' | 'info' | 'warning' | 'error';
    children: React.ReactNode;
}

const NoData: React.FC<NoDataProps> = ({ type = "dflt", children }) => {
    return (
        <div className={styles[type]}>
            {children}
        </div>
    );
};

export default NoData;
