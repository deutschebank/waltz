import React from "react";
import style from "./Toast.module.css"

export default function Toasts() {
    return (
        <div className={style.toastContainer}>
            <div className={style.toastPane} id={"react-toasts-pane"}>
            </div>
        </div>
    )
}