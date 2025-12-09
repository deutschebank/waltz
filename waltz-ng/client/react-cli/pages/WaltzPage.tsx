import React, {useEffect} from "react";
import {Outlet, useLocation} from "react-router";

const WaltzPage = () => {
    const location = useLocation();
    useEffect(() => {
        console.log(location)
    }, [location]);
  return (
    <div>
      <Outlet />
    </div>
  );
};

export default WaltzPage;
