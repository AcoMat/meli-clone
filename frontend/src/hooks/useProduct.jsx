import { useCallback, useEffect, useState } from "react";
import { addQuestionToProduct, addResponseToProduct, getProductById } from "../services/AxiosService";

export default function useProduct(productId) {
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchData = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const data = await getProductById(productId);
      setProduct(data);
    } catch (err) {
      setError(`Error al cargar los datos: ${err.message}`);
    } finally {
      setLoading(false);
    }
  }); 

  useEffect(() => {
    fetchData();
  }, [productId]); 

  const addQuestion = async (question) => {
    await addQuestionToProduct(productId, {"text":question});
    fetchData();
  }

  const addResponse = async (questionId, response) => {
    await addResponseToProduct(productId, questionId, {"text":response});
    fetchData();
  }
 

  return { product, loading, error, addQuestion, addResponse };
}