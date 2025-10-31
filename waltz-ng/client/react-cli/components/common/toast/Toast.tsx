import React from "react";
import {ToastCreateType} from "../../../types/Toast";
import {createPortal} from "react-dom";
import ToastInternal from "./ToastInternal";

export default function Toast({type, message}: ToastCreateType) {
    const toastRoot = document.getElementById("react-toasts-pane");
    if(!toastRoot) return <></>;

    return (
        createPortal(<ToastInternal type={type} message={message}/>, toastRoot)
    );
}