import React, { useState, useEffect } from "react";
import PopoverContent from "./PopoverContent";
import styles from "./Popover.module.scss";
import { useSliceSelector } from "../../../hooks/useSliceSelector";
import reduxStore from "../../../../redux-store";
import { dismissPopover } from "../../../../redux-slices/popover-slice";

/**
 * Popover displays popover, managed via the Redux popover state.
 */
const Popover: React.FC = () => {
    // Retrieves the current popover data from the Redux store.
    const popover = useSliceSelector((state) => state.popover.current);
    // State to store the height of the popover content.
    const [height, setHeight] = useState(100);

    // Callback ref to measure the height of the popover's content.
    const measuredRef = React.useCallback((node: HTMLDivElement) => {
        if (node !== null) {
            setHeight(node.clientHeight);
        }
    }, []);

    // Effect to handle the 'Escape' key press to dismiss the popover.
    useEffect(() => {
        const handleKeyDown = (event: KeyboardEvent) => {
            if (popover && event.key === "Escape") {
                onDismissPopover();
            }
        };

        // Adds and removes the keydown event listener.
        window.addEventListener("keydown", handleKeyDown);
        return () => {
            window.removeEventListener("keydown", handleKeyDown);
        };
    }, [popover]);

    if (!popover) {
        return null; // If no popover is active, render nothing.
    }

    // Dispatches an action to dismiss the popover.
    const onDismissPopover = () => reduxStore.dispatch(dismissPopover());

    // Gets the component to be rendered inside the popover, if any.
    const Component = popover?.component || null;

    // Renders the popover with a backdrop and its content.
    return (
        <>
            {/* The backdrop screen that dismisses the popover on click. */}
            <div
                className={styles.waltzBackingScreen}
                onClick={onDismissPopover}
                aria-label="Close Popover"
            ></div>

            <section className={styles.waltzPopover}>
                {/* The main content of the popover. */}
                <PopoverContent onDismiss={onDismissPopover}>
                    {popover.title && <h4>{popover.title}</h4>}
                    <div
                        // Applies a scrollable region if the content height exceeds 400px.
                        className={
                            height > 400 ? "waltz-scroll-region-400" : ""
                        }
                    >
                        <div ref={measuredRef}>
                            {/* Renders a dynamic component if provided. */}
                            {Component && <Component {...popover.props} />}
                            {/* Renders HTML content if provided. */}
                            {popover.content && (
                                <div
                                    dangerouslySetInnerHTML={{
                                        __html: popover.content,
                                    }}
                                />
                            )}
                        </div>
                    </div>
                </PopoverContent>
            </section>
        </>
    );
};

export default Popover;