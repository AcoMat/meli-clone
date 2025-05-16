import { useState, useEffect } from "react";
import { getUserFavorites } from "../services/ApiService";
import { getToken } from "../services/tokenService";

export default function useFavorites() {
    const [favorites, setFavorites] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        getToken().then(token => {
            if (!token) {
                setLoading(false);
                return;
            }
            getUserFavorites(token)
                .then(data => {
                    setFavorites(data);
                    setLoading(false);
                })
                .catch(err => {
                    setLoading(false);
                });
        });
    }, []);

    return { favorites, loading };
}