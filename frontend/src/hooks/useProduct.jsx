import { useEffect, useState } from "react";
import { getProductById } from "../services/ApiService";

export default function useProduct(productId) {
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);

  const fetchProduct = async () => {
    const product = await getProductById(productId);
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