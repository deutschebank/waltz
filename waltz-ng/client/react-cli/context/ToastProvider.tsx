import React, { useState, useCallback } from "react";
import { ToastContext } from "./ToastContext";
import Toast from "../components/common/toast/Toast";
import Toasts from "../components/common/toast/Toasts";
import { ToastCreateType } from "../types/Toast";

interface ToastProviderProps {
    children: React.ReactNode;
}

/**
 * ToastProvider manages the state for displaying toast notifications throughout the application.
 */
export const ToastProvider: React.FC<ToastProviderProps> = ({ children }) => {
    // State to hold the array of active toast notifications.
    const [toasts, setToasts] = useState<ToastCreateType[]>([]);

    // Callback to add a new toast to the list, ensuring the function identity is stable.
    const addToast = useCallback((toast: ToastCreateType) => {
        setToasts((prevToasts) => [...prevToasts, toast]);
    }, []);

    // Provides the addToast function to its children and renders the toasts.
    return (
        <ToastContext.Provider value={{ addToast }}>
            {children}
            <Toasts>
                {toasts.map((toast, i) => (
                    <Toast key={i} {...toast} />
                ))}
            </Toasts>
        </ToastContext.Provider>
    );
};