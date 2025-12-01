import React from "react";
import useActorRoutes from "./useActorRoutes";
import usePlaypenRoutes from "./usePlaypenRoutes";
import useUserRoutes from "./useUserRoutes";

// Routes defined in this file should be defined in a non-decreasing alphabetical order
// Each route hook should also internally have its routes defined in a non-decreasing alphabetical order
const useWaltzRoutes = () => {
  const actorRoutes = useActorRoutes();
  const playpenRoutes = usePlaypenRoutes();
  const userRoutes = useUserRoutes();
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
