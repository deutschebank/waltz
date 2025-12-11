import React from "react";
import style from "./Toast.module.css"
interface ToastsProps {
    children: React.ReactNode;
}

export default function Toasts({ children }: ToastsProps) {
    return (
        <div className={style.toastContainer}>
            <div className={style.toastPane} id={"react-toasts-pane"}>{children}</div>
        </div>
    )
}