import { useEffect, useState } from "react";
import { getRelated } from "../services/AxiosService";

export default function useRelatedProducts(productId) {
  const [relatedProducts, setRelatedProducts] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    setError(null);
    try {
      getRelated(productId)
      .then(data => setRelatedProducts(data.products));
    } catch (err) {
      setError(`Error al cargar los datos: ${err.message}`);
    } finally {
      setLoading(false);
    }
  }, []); 

  return { relatedProducts, loading, error };
}