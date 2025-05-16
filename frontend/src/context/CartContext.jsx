import { createContext, useContext } from "react";

export const CartContext = createContext()

export const CartProvider = ({ children }) => {

    const addProductToCart = (a,b) => {}

    return (
        <CartContext.Provider value={{ addProductToCart }}>
            {children}
        </CartContext.Provider>
    );
};

export const useCartContext = () => useContext(CartContext);