import React from "react";
import style from "./Toast.module.css"
import {useSliceSelector} from "../../../hooks/useSliceSelector";
import Toast from "./Toast";
import {ToastType} from "../../../types/Toast";

export default function Toasts() {
    const toasts = useSliceSelector<ToastType[]>(state => state.toasts);
    return (
        <div className={style.toastContainer}>
            <div className={style.toastPane}>
                {toasts.map(obj => {
                    return <Toast
                        id={obj.id}
                        type={obj.type}
                        message={obj.message}/>
                    }
                )}
            </div>
        </div>
    )
}