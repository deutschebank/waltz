// src/App.tsx
import React from "react";
import AppRouter from "./router/AppRouter";
import ErrorBoundary from "./ErrorBoundary";
import { ToastProvider } from "./context/ToastProvider";

const App: React.FC = () => (
    <React.StrictMode>
        <ErrorBoundary>
            <ToastProvider>
                <AppRouter />
            </ToastProvider>
        </ErrorBoundary>
    </React.StrictMode>
);

export default App;