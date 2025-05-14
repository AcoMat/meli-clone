import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter, RouterProvider } from "react-router-dom"

import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap'

import "./index.css";

import Layout from "./pages/Layout.jsx";
import Home from "./pages/Home.jsx"
import ErrorPage from "./pages/ErrorPage.jsx";
import Login from "./pages/Login.jsx"
import Register from './pages/Register.jsx';
import Product from './pages/Product.jsx';
import Categories from './pages/Categories.jsx';
import Category from './pages/Category.jsx';
import User from './pages/User.jsx';
import Cart from './pages/Cart.jsx';
import Search from './pages/Search.jsx';
import Favorites from './pages/Favorites.jsx';
import { UserProvider } from './context/AuthContext.jsx';
import { CartProvider } from './context/CartContext.jsx';
import Purchases from './pages/Purchases.jsx';
import Checkout from './pages/Checkout.jsx';

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
        path: "/login",
        element: <Login />
      },
      {
        path: "/register",
        element: <Register />
      },
      {
        path: "/categories",
        element: <Categories />
      },
      {
        path: "/categories/:id",
        element: <Category />
      },
      {
        path: "/user",
        element: <User />

      },
      {
        path: "/cart",
        element: <Cart />
      },
      {
        path: "search",
        element: <Search />
      },
      {
        path: "checkout",
        element: <Checkout />
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
