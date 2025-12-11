import React from "react";
import Icon from "../Icon";
import styles from "./PopoverContent.module.scss";
import Button from "../button/Button";

// Defines the props for the PopoverContent component.
interface PopoverContentProps {
    children: React.ReactNode;
    dismissible?: boolean;
    onDismiss?: () => void;
}

/**
 * PopoverContent renders the inner content of a popover, including an optional dismiss button.
 */
const PopoverContent: React.FC<PopoverContentProps> = ({
    children,
    dismissible = true,
    onDismiss,
}) => {
    // Renders the popover article with a dismiss button and the main content.
    return (
        <article>
            {/* Renders a dismiss button if the popover is dismissible. */}
            {dismissible && (
                <Button
                    className={`close ${styles.transparent}`}
                    onClick={onDismiss!}
                    aria-label="Close"
                >
                    <Icon name="times" />
                </Button>
            )}
            {/* Renders the children passed to the component. */}
            <div className={styles.text}>{children}</div>
        </article>
    );
};

export default PopoverContent;