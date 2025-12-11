import React from 'react';
import Icon from '../Icon';
import styles from './Toggle.module.css';

interface ToggleProps {
    iconOn?: string;
    iconOff?: string;
    labelOn?: string;
    labelOff?: string;
    readOnly?: boolean;
    state?: boolean;
    onToggle?: () => void;
}

const Toggle: React.FC<ToggleProps> = ({
    iconOn = "toggle-on",
    iconOff = "toggle-off",
    labelOn,
    labelOff,
    readOnly,
    state,
    onToggle = () => console.log("toggle")
}) => {

    const content = state
        ? (
            <span>
                <Icon name={iconOn}/>
                <span>{labelOn}</span>
            </span>
        )
        : (
            <span>
                <Icon name={iconOff}/>
                <span>{labelOff}</span>
            </span>
        );

    return (
        <>
            {!readOnly
                ? (
                    <button
                        className={`btn btn-skinny no-text-select ${styles.button}`}
                        style={{textDecoration: "none"}}
                        onClick={() => onToggle()}>
                        {content}
                    </button>
                )
                : content}
        </>
    );
};

export default Toggle;
