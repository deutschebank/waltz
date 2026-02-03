import React from "react";
import styles from "./Button.module.scss";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    children: React.ReactNode;
    onClick?: () => void;
    className?: string;
    type?: "button" | "submit" | "reset" | undefined;
}

// Render Button Component
const Button: React.FC<ButtonProps> = ({
    type = "button",
    children,
    onClick,
    className = "",
    ...rest
}) => {
    return (
        <button
            data-testid="custom-button"
            className={`${className} ${styles.mr}`}
            onClick={onClick}
            {...rest}
        >
            {children}
        </button>
    );
};

export default Button;