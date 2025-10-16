// src/components/Button/Button.tsx
import React, { FC, MouseEvent } from "react";
import "./Button.module.scss";

type ButtonProps = {
    label: string;
    onClick: (event: MouseEvent<HTMLButtonElement>) => void;
    disabled?: boolean;
    variant?: "primary" | "secondary";
};

const Button: FC<ButtonProps> = ({
    label,
    onClick,
    disabled = false,
    variant = "primary",
}) => {
    return (
        <button
            className={`btn ${
                variant === "primary" ? "btn-primary" : "btn-secondary"
            }`}
            onClick={onClick}
            disabled={disabled}
        >
            {label}
        </button>
    );
};

// Export the memoized Button component
export default React.memo(Button);
