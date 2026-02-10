import React, {useRef, useEffect} from "react";
import tippy, {Props as TippyProps} from 'tippy.js';
import 'tippy.js/dist/tippy.css';
import 'tippy.js/themes/light-border.css';
import styles from "./Tooltip.module.css";

interface TooltipProps {
    content: React.ReactNode | null;
    trigger?: string;
    placement?: TippyProps['placement'];
    delay?: [number, number];
    children: React.ReactNode;
    showTooltip?: boolean;
}

const Tooltip: React.FC<TooltipProps> = ({
    content,
    trigger = "mouseenter click",
    placement = "top",
    delay = [300, 100],
    children,
    showTooltip = true
}) => {
    const targetRef = useRef<HTMLSpanElement>(null);
    const contentRef = useRef<HTMLDivElement>(null);
    const tippyInstanceRef = useRef<any>(null);

    useEffect(() => {
        if (targetRef.current && contentRef.current) {
            tippyInstanceRef.current = tippy(targetRef.current, {
                content: contentRef.current,
                arrow: true,
                interactive: true,
                appendTo: document.body,
                trigger,
                theme: "light-border",
                placement,
                delay
            });
        }

        return () => {
            if (tippyInstanceRef.current) {
                tippyInstanceRef.current.destroy();
            }
        };
    }, [content, trigger, placement, delay]);

    return (
        <>
            <span ref={targetRef}>
                {children}
            </span>

            {/* required to be none, else will be visible on the DOM */}
            {showTooltip && content &&
                <div className={styles.content}>
                    <div ref={contentRef} className={styles.contentRef}>{content}</div>
                </div>
            }
        </>
    );
};

export default Tooltip;
