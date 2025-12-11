import React from "react";
import {Route} from "react-router";
import WaltzPage from "../../pages/WaltzPage";
import Playpen7Page from "../../pages/playpen/Playpen7Page";
import About from "../../pages/About";
import Home from "../../pages/Home";
import UserManagementPanel from "../../pages/user/UserManagementPanel";
import Playpen6Page from "../../pages/playpen/Playpen6Page";

const PlaypenRoutes = () => {
  return (
    <>
      <Route path="playpen" element={<WaltzPage />}>
        {/* playpen6 is dependent on props sent from angular scope */}
        <Route path="6" element={<Playpen6Page />} />
        <Route path="7" element={<Playpen7Page />}>
          <Route path="about" element={<About />} />
          <Route path="react" element={<Home />} />
          <Route path="user" element={<UserManagementPanel />} />
        </Route>
      </Route>
    </>
  );
};

export default PlaypenRoutes;
