import { createContext, useContext } from "react";
import { ToastCreateType } from "../types/Toast";

// Defines the shape of the context value provided by the ToastProvider.
interface ToastContextType {
    addToast: (toast: ToastCreateType) => void;
}

// Creates a React context for managing toast notifications.
export const ToastContext = createContext<ToastContextType | undefined>(
    undefined
);

// Custom hook to simplify accessing the toast context.
export const useToasts = () => useContext(ToastContext)!;