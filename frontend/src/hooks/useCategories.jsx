import { useCallback, useEffect, useState } from "react";
import { getAllCategories } from "../services/AxiosService";

export function useCategories() {
  const [categories, setCategories] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchData = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getAllCategories()
      setCategories(data);
    } catch (err) {
      setError('Error al cargar los datos: ', err.message);
      setCategories(null);
    } finally {
      setLoading(false);
    }
  });

  useEffect(() => {
    fetchData();
  }, []);

  return { categories, loading, error };
};