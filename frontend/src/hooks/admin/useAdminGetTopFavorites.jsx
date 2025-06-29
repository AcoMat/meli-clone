import { useEffect, useState } from "react";
import { getToken } from "../../services/TokenService";
import { adminGetTopFavorites } from "../../services/ApiService";

export default function useAdminGetTopFavorites() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = async () => {
    try {
        const token = await getToken();
        if (!token) {
            throw new Error("No authentication token found");
        }
        const data = await adminGetTopFavorites(token);
        setProducts(data);
    } catch (err) {
        setError(err.message);
    } finally {
        setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  return { products, loading, error };
}