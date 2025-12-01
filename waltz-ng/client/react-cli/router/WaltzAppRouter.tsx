import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router";
import NotFound from "../pages/not-found/NotFound";
import useWaltzRoutes from "../hooks/router/useWaltzRoutes";

const WaltzAppRouter = () => {
  const routes = useWaltzRoutes();

  return (
    <Router>
      <Routes>
        {routes}

        {/* Not found at the '/*' level */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </Router>
  );
};

export default WaltzAppRouter;
