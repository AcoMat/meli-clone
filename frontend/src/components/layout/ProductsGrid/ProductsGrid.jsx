import ProductCard from "../../cards/ProductCard/ProductCard";
import { useNavigate } from "react-router-dom";

function ProductGrid( { products } ) {
    const navigate = useNavigate();

    return (
        <div className="mx-auto row my-3 row-gap-3">
            {products &&
                products.map((product) => (
                    <div className="col" key={product.id}>
                        <ProductCard 
                            product={product} 
                            clickfnc={navigate}
                        />
                    </div>
                ))
            }
        </div>
    );
}

export default ProductGrid;