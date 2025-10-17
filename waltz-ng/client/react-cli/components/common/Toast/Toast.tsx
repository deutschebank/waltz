import React, {useEffect, useState} from "react";
import styles from "./Toast.module.css";
import Icon from "../Icon";
import {NOTIFICATION_TYPES} from "../../../constants/notification";
import {ToastType} from "../../../types/Toast";
import reduxStore from "../../../../redux-store";
import {removeToast} from "../../../../redux-slices/toast-slice";

export default function Toast({id, type, message}: ToastType) {
    useEffect(() => {
        const timeoutHandler = setTimeout(()=> {
            reduxStore.dispatch(removeToast({id}))
        }, 2000);
        return () => clearTimeout(timeoutHandler);
    }, [id]);


    return (
        <div className={styles.toast} style={{background: `${NOTIFICATION_TYPES[type]?.color}`}}>
            <div className={styles.toastIcon}>
                <Icon name={NOTIFICATION_TYPES[type]?.iconName}
                      size={"lg"}/>
            </div>
            {message}
        </div>
    );
}