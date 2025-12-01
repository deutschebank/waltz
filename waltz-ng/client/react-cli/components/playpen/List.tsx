import React from "react";

const List: React.FC = () => (
  <nav>
    {/* In bounds for react router to show Not Found if the path os not found*/}
    <a href="playpen/7/react">Home</a> |<a href="playpen/7/about">About</a> |
    <a href="playpen/7/user">User Management</a> |<a href="playpen/7/support">Support</a>{" "}
    |<a href="playpen/7/okay">playpen/7/okay</a> |
    {/* Out of bounds for react-router as it is not configured as a wildcard in angular scope */}
    <a href="playpen/okay">playpen/okay</a> |<a href="okay">okay</a>
  </nav>
);

export default List;
