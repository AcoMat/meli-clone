import { useCallback, useEffect, useState } from "react";
import { getCategoryByID } from "../services/AxiosService";

export function useCategory() {
  const [categoryID, setCategoryID] = useState(null);
  const [page, setPage] = useState(1);
  const [productsPage, setProductsPage] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchData = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getCategoryByID(categoryID, page)
      setProductsPage(data);
    } catch (err) {
      setError('Error al cargar los datos: ', err.message);
    } finally {
      setLoading(false);
    }
  });

  useEffect(() => {
    fetchData(categoryID,page);
  }, [categoryID,page]);

  return { setCategoryID, setPage, productsPage, loading, error };
};