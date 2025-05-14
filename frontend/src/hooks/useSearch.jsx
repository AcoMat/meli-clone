import { useCallback, useEffect, useState } from "react";
import { searchProducts } from "../services/AxiosService";

export default function useSearch() {
  const [productsPage, setProductsPage] = useState(null);
  const [page, setPage] = useState(1);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchText, setSearchText] = useState(null);

  const fetchData = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await searchProducts(searchText, page)
      setProductsPage(data);
    } catch (err) {
      setError('Error al cargar los datos');
    } finally {
      setLoading(false);
    }
  },[searchText]); 

  useEffect(() => {
    searchText ? fetchData(searchText, page) : null;
  }, [searchText]);


  return { setSearchText, setPage, productsPage, loading, error };
};