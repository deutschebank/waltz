// src/components/button/Button.tsx
import React, { FC, MouseEvent } from "react";
import styles from "./Button.module.scss";

// Type
type ButtonProps = {
    label: React.ReactNode;
    onClick: () => void;
    disabled?: boolean;
    className?: string;
};

// Render Button Component
const Button: FC<ButtonProps> = ({
    label,
    onClick,
    disabled = false,
    className = "",
}) => {
    return (
        <button
            data-testid="custom-button"
            className={className}
            onClick={onClick}
            disabled={disabled}
        >
            {label}
        </button>
    );
};

export default Button;
