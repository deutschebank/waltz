// src/router/AppRouter.tsx
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router";
import Home from "../pages/Home";
import About from "../pages/About";
import NotFound from "../pages/NotFound";
import List from "../components/list/List";

const AppRouter: React.FC = () => (
    <Router>
        <List />
        <Routes>
            <Route path="playpen/7/react" element={<Home />} />
            <Route path="playpen/7/about" element={<About />} />
            <Route path="playpen/7/*" element={<NotFound />} />
        </Routes>
    </Router>
);

export default AppRouter;
