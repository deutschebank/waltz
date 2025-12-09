import React from "react";
import ActorRoutes from "../../router/routes/Actor";
import PlaypenRoutes from "../../router/routes/Playpen";
import UserRoutes from "../../router/routes/User";

// Routes defined in this file should be defined in a non-decreasing alphabetical order
// Each route should also internally have its routes defined in a non-decreasing alphabetical order
const useWaltzRoutes = () => {
  const actorRoutes = ActorRoutes();
  const playpenRoutes = PlaypenRoutes();
  const userRoutes = UserRoutes();
  return (
    <>
      {/* Every base page (e.g. /playpen or /logical-flow MUST be a 'WaltzPage' */}
      {actorRoutes}
      {playpenRoutes}
      {userRoutes}
    </>
  );
};

export default useWaltzRoutes;
