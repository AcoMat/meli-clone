import Header from '../components/layout/Header/Header.jsx';
import { Outlet } from "react-router-dom";
import SimpleHeader from '../components/layout/Header/Simpleheader.jsx';

export default function Layout() {
  return (
    <>
      {location.pathname == "/login" || location.pathname == "/register" ?
        <SimpleHeader />
        :
        <Header submitfcn={(e) => handleSearch(e, e.target.value)} />
      }
      <Outlet />
    </>
  );
}
