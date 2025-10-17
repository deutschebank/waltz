// src/components/button/Button.tsx
import React, { FC, MouseEvent } from "react";
import styles from "./Button.module.scss";

type ButtonProps = {
    label: string;
    onClick: (event: MouseEvent<HTMLButtonElement>) => void;
    disabled?: boolean;
    className?: string;
};

const Button: FC<ButtonProps> = ({
    label,
    onClick,
    disabled = false,
    className = "",
}) => {
    return (
        <button className={className} onClick={onClick} disabled={disabled}>
            {label}
        </button>
    );
};

export default Button;
