import React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router";
import NotFound from "../pages/not-found/NotFound";
import useWaltzRoutes from "../hooks/router/useWaltzRoutes";

const WaltzAppRouter = () => {

  // get basename href from the <base> tag and use that as the basename for react-based routing
  const basename = document
    .getElementsByTagName("base")[0]
    .getAttribute("href")
    ?? "/";

  const routes = useWaltzRoutes();

  return (
    <Router basename={basename}>
      <Routes>
        {routes}
        {/* Not found at the '/*' level */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default WaltzAppRouter;
