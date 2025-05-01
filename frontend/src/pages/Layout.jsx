import Header from '../components/layout/Header/Header.jsx';
import { Outlet, useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import SimpleHeader from '../components/layout/Header/Simpleheader.jsx';
import { useCallback } from 'react';

export default function Layout() {
  let navigate = useNavigate();
  let location = useLocation();

  const handleSearch = useCallback((event, value) => {
    event.preventDefault();
    const queryValue = event.target.elements.query.value.trim();
    if (queryValue) {
      navigate(`/search?query=${queryValue}`);
    }
  });

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
