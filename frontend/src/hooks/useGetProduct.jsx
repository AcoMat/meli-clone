import { useEffect, useState } from "react";
import { getProductById, getProductQuestions, getProductsReviews } from "../services/ApiService";

export default function useGetProduct(productId) {
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchProduct = async () => {
    const product = await getProductById(productId);
    if(!product) {
      setProduct(null);
      setLoading(false);
      return;
    }
    const questions = await getProductQuestions(productId);
    const reviews = await getProductsReviews(productId);
    product.questions = questions;
    product.reviews = reviews;
    setProduct(product);
    setLoading(false);
  }

  const refresh = async () => {
    setLoading(true);
    fetchProduct();
  }

  useEffect(() => {
    fetchProduct();
  }, [productId]);

  return { product, loading, refresh };
}