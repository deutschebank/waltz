import React, {use, useEffect} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router";
import NotFound from "../pages/not-found/NotFound";
import useWaltzRoutes from "../hooks/router/useWaltzRoutes";
import WaltzPage from "../pages/WaltzPage";
import ActorListView from "../pages/actor/ActorListView";
import Playpen6Page from "../pages/playpen/Playpen6Page";
import Playpen7Page from "../pages/playpen/Playpen7Page";
import About from "../pages/About";
import Home from "../pages/Home";
import UserManagementPanel from "../pages/user/UserManagementPanel";

const WaltzAppRouter = () => {
  const routes = useWaltzRoutes();

  return (
    <Router basename={"waltz-web"}>
      <Routes>
        {routes}
          {/*<Route path="actor" element={<WaltzPage />}>*/}
          {/*    <Route path="list" element={<ActorListView />} />*/}
          {/*</Route>*/}

          {/*<Route path="playpen" element={<WaltzPage />}>*/}
          {/*    /!* playpen6 is dependent on props sent from angular scope *!/*/}
          {/*    <Route path="6" element={<Playpen6Page />} />*/}
          {/*    <Route path="7" element={<Playpen7Page />}>*/}
          {/*        <Route path="about" element={<About />} />*/}
          {/*        <Route path="react" element={<Home />} />*/}
          {/*        <Route path="user" element={<UserManagementPanel />} />*/}
          {/*    </Route>*/}
          {/*</Route>*/}

          {/*<Route path="user" element={<WaltzPage />}>*/}
          {/*    <Route path="management" element={<UserManagementPanel />} />*/}
          {/*</Route>*/}

        {/* Not found at the '/*' level */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default WaltzAppRouter;
