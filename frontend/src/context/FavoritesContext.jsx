import { createContext, useContext, useEffect, useState } from "react";
import { getToken } from "../services/TokenService";
import { getUserFavorites, putFavoriteProduct } from "../services/ApiService";

export const FavoritesContext = createContext();

export function FavoritesProvider({ children }) {
    const [loading, setLoading] = useState(true);
    const [favorites, setFavorites] = useState(null);

    const fetchData = async () => {
        setLoading(true);
        const token = await getToken();
        if (!token) {
            setLoading(false);
            return null;
        }
        const favorites = await getUserFavorites(token);
        setFavorites(favorites);
        setLoading(false);
    };

    useEffect(() => {
        fetchData()
    }, []);

    const toggleFavoriteProduct = async (productId) => {
        setLoading(true);
        const token = await getToken();
        if (!token) {
            setLoading(false);
            return;
        }
        await putFavoriteProduct(token, productId);
        const favorites = await getUserFavorites(token);
        setFavorites(favorites);
        setLoading(false);
    }

    return (
        <FavoritesContext.Provider value={{ favorites, loading, toggleFavoriteProduct }}>
            {children}
        </FavoritesContext.Provider>
    );
}
export const useFavoritesContext = () => useContext(FavoritesContext);