import React from "react";
import WaltzPage from "../../pages/WaltzPage";
import {Route} from "react-router";
import ActorListView from "../../pages/actor/ActorListView";

const useActorRoutes = () => {
  return (
    <>
      <Route path="actor" element={<WaltzPage />}>
        <Route path="list" element={<ActorListView />} />
      </Route>
    </>
  );
};

export default useActorRoutes;
