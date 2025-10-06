import React from 'react';
import styles from './SubSection.module.css';

interface SubSectionProps {
    showBorder?: boolean;
    header: React.ReactNode;
    content: React.ReactNode;
    controls?: React.ReactNode;
    changes?: React.ReactNode;
}

const SubSection: React.FC<SubSectionProps> = ({ showBorder = true, header, content, controls, changes }) => {
    return (
        <div className={`${styles.subSection} ${showBorder ? styles.showBorder : ''}`}>
            <div className={styles.name}>
                {header}
            </div>

            <div className={styles.content}>
                {content}
            </div>

            {controls &&
            <div className={styles.controls}>
                {controls}
            </div>
            }

            {changes &&
            <div className={styles.content}>
                {changes}
            </div>
            }
        </div>
    );
};

export default SubSection;
