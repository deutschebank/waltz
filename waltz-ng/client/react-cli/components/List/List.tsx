// src/components/list/List.tsx
import React from "react";
import { NavLink } from "react-router";

const List: React.FC = () => (
    <nav>
        <NavLink to="/playpen/7/react">Home</NavLink> |{" "}
        <NavLink to="/playpen/7/about">About</NavLink> |{" "}
        <NavLink to="/playpen/7/user">User Management</NavLink> |{" "}
        <NavLink to="/playpen/7/support">Support</NavLink>
    </nav>
);

export default List;