import React from "react";
import List from "../../components/playpen/List";
import {Outlet} from "react-router";

const Playpen7Page = () => {
  return (
    <div>
      <List />
      <Outlet />
    </div>
  );
};

export default Playpen7Page;
