import React from "react";
import {Route} from "react-router";
import WaltzPage from "../../pages/WaltzPage";
import UserManagementPanel from "../../pages/user/UserManagementPanel";

// can there be a better name for this ?
const UserRoutes = () => {
  return (
    <>
      <Route path="user" element={<WaltzPage />}>
        <Route path="management" element={<UserManagementPanel />} />
      </Route>
    </>
  );
};

export default UserRoutes;
