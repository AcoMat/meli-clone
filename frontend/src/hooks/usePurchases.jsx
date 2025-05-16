import { useState, useEffect } from "react";
import { getUserPurchases } from "../services/ApiService";
import { getToken } from "../services/tokenService";

export default function usePurchases() {
    const [purchases, setPurchases] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        getToken().then(token => {
            if (!token) {
                setLoading(false);
                return;
            }
            getUserPurchases(token)
                .then(data => {
                    setPurchases(data);
                    setLoading(false);
                })
                .catch(err => {
                    setLoading(false);
                });
        });
    }, []);

    return { purchases, loading };
}