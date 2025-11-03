import React, {useEffect, useState} from "react";
import {ToastCreateType} from "../../../types/Toast";
import styles from "./Toast.module.css";
import {NOTIFICATION_TYPES} from "../../../constants/notification";
import Icon from "../Icon";

export default function ToastInternal({type, message}: ToastCreateType) {
    const [visible, setVisible] = useState(true);
    const [isHovering, setIsHovering] = useState(false);
    const onDismiss = () => setVisible(false);

    useEffect(() => {
        let timeout: NodeJS.Timeout;

        if (!isHovering) {
            timeout = setTimeout(() => {
                setVisible(false);
            }, 15000);
        }

        return () => {
            if (timeout) {
                clearTimeout(timeout);
            }
        };
    }, [isHovering]);

    return (
        visible ?
            <div className={styles.toast}
                 style={{background: `${NOTIFICATION_TYPES[type]?.color}`}}
                 onMouseEnter={() => setIsHovering(true)}
                 onMouseLeave={() => setIsHovering(false)}>
                <div className={styles.toastIcon}>
                    <Icon name={NOTIFICATION_TYPES[type]?.iconName}
                          size={"lg"}/>
                </div>
                <div className={styles.toastText}>{message}</div>
                <div className={styles.toastDismiss}
                    onClick={onDismiss}>
                    <Icon name="xmark"
                          size="sm"/>
                </div>
            </div>
            : <></>
    );
}