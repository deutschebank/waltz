// src/router/AppRouter.tsx
import React from "react";
import { BrowserRouter as Router, Route, Routes, Outlet } from "react-router";
import Home from "../pages/Home";
import About from "../pages/About";
import NotFound from "../pages/NotFound";
import UserManagementPanel from "../pages/user/UserManagementPanel";
import List from "../components/List/List";

const AppLayout: React.FC = () => (
    <>
        <List />
        <Outlet />
    </>
);

const AppRouter: React.FC = () => (
    <Router>
        <Routes>
            <Route path="playpen/7/" element={<AppLayout />}>
                <Route path="react" element={<Home />} />
                <Route path="about" element={<About />} />
                <Route path="user" element={<UserManagementPanel />} />
                <Route path="*" element={<NotFound />} />
            </Route>
        </Routes>
    </Router>
);

export default AppRouter;