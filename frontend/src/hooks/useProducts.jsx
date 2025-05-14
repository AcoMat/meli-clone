import { useCallback, useEffect, useState } from "react";
import { getProductsPage } from "../services/AxiosService";

export default function useProducts() {
    const [productsPage, setProductsPage] = useState(null);
    const [page, setPage] = useState(1);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const fetchData = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
          const data = await getProductsPage(page)
          setProductsPage(data);
        } catch (err) {
          setError('Error al cargar los datos');
        } finally {
          setLoading(false);
        }
      },[page]); 

    useEffect(() => {
        fetchData();
    }, []);


    return { productsPage, loading, error, setPage };
};