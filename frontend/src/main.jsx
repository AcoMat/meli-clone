import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from "react-router-dom"

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap'

import "./index.css";
import Home from './pages/Home';
import Product from './pages/product/Product';
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';
import User from './pages/user/User';
import Cart from './pages/user/Cart';
import Search from './pages/product/Search';
import Favorites from './pages/user/Favorites';
import Purchases from './pages/user/Purchases';
import ErrorPage from './pages/ErrorPage';
import Layout from './pages/Layout';
import { UserProvider } from './context/UserContext';
import { CartProvider } from './context/CartContext';
import Review from './pages/product/Review';


const router = createBrowserRouter([
  {
    element: <Layout />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "product/:idProduct",
        element: <Product />,
      },
      {
        path: "login",
        element: <Login />
      },
      {
        path: "register",
        element: <Register />
      },
      {
        path: "user",
        element: <User />

      },
      {
        path: "cart",
        element: <Cart />
      },
      {
        path: "search",
        element: <Search />
      },
      {
        path: "favorites",
        element: <Favorites />
      },
      {
        path: "purchases",
        element: <Purchases />
      },
      {
        path: "product/:idProduct/review",
        element: <Review />
      },
      {
        path: "error",
        element: <ErrorPage />
      }
      ]
  }

]);


createRoot(document.getElementById('root')).render(
  <StrictMode>
    <UserProvider>
      <CartProvider>
        <RouterProvider router={router} />
      </CartProvider>
    </UserProvider>
  </StrictMode>,
)
