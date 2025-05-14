import { createContext, useContext, useEffect, useState } from "react";
import { addToCart, getCart, purchase, removeFromCart } from "../services/AxiosService";
import { useUserContext } from "./AuthContext";

export const CartContext = createContext()

export const CartProvider = ({ children }) => {
    const [cart, setCart] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const { user, update } = useUserContext();

    const addProductToCart = async (productId, amount) => {
        setLoading(true);
        setError(null);
        try {
            const cart = await addToCart(productId, amount);
            setCart(cart);
        } catch (err) {
            setError(err.message)
        } finally {
            setLoading(false);
        }
    }

    const removeProductFromCart = async (productId) => {
        setLoading(true);
        setError(null);
        try {
            const cart = await removeFromCart(productId);
            setCart(cart);
        } catch (err) {
            console.log(err.message);
        } finally {
            setLoading(false);
        }
    }

    const purchaseCart = async (paymentBody) => {
        setLoading(true);
        setError(null);
        try {
            await purchase(paymentBody);
            setCart(null);
            update();
        } catch (err) {
            setError(err.response.data.title);
        } finally {
            setLoading(false);
        }
    }

    useEffect(() => {
        if (!user) {
            setCart(null);
        } else {
            getCart()
                .then((cart) => setCart(cart))
                .catch(err => setError(err.message))
        }
    }, [user]);

    return (
        <CartContext.Provider value={{ cart, loading, error, addProductToCart, purchaseCart, removeProductFromCart }}>
            {children}
        </CartContext.Provider>
    );
};

export const useCartContext = () => useContext(CartContext);