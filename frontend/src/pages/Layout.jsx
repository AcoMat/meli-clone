import Header from '../components/layout/Header/Header.jsx';
import { Outlet, useLocation } from "react-router-dom";
import SimpleHeader from '../components/layout/Header/Simpleheader.jsx';

export default function Layout() {
  const location = useLocation();
  return (
    <>
      {location.pathname == "/login" || location.pathname == "/register" ?
        <SimpleHeader />
        :
        <Header/>
      }
      <Outlet />
    </>
  );
}
