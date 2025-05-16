import { useState } from "react";

export default function useProduct(productId) {
  const [product, setProduct] = useState(null);

  return { product };
}