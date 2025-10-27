// src/App.tsx
import React from "react";
import AppRouter from "./router/AppRouter";
import ErrorBoundary from "./ErrorBoundary";
// import { Provider } from "react-redux";
// import { store } from "./store";

const App: React.FC = () => (
    <React.StrictMode>
        <ErrorBoundary>
            {/* <Provider store={store}> */}
            <AppRouter />
            {/* </Provider> */}
        </ErrorBoundary>
    </React.StrictMode>
);

export default App;
