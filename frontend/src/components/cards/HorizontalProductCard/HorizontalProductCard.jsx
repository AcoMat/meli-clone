import { useCartContext } from "../../../context/CartContext"
import { useNavigate } from "react-router-dom";
import { toggleFavorite } from "../../../services/ApiService";
import { getToken } from "../../../services/TokenService";

export default function HorizontalProductCard({ product }) {
    let navigate = useNavigate();
    const { addToCart } = useCartContext();

    return (
        <div className="p-2 pb-4 d-flex border-bottom" onClick={() => navigate(`/product/${product.id}`)} style={{ cursor: "pointer" }}>
            <img className="img-fluid" src={product.pictures[0]} style={{ maxHeight: "180px" }} />
            <div className="d-flex px-2 w-75 flex-column justify-content-between">
                <div>
                    <h4 className="fs-4 text-break">{product.name}</h4>
                    {product.priceDiscountPercentage && <span className="text-decoration-line-through fw-light">${product.price?.toFixed(2)}</span>}
                    {product.priceDiscountPercentage ?
                        <h5>$ {product.price?.toFixed(2) * (product.priceDiscountPercentage / 100)}<span className="text-success fw-light"> {product.priceDiscountPercentage}% OFF</span></h5>
                        :
                        product.price && <h5>$ {product.price?.toFixed(2)}</h5>
                    }
                    {product.price && <p className="fw-light">en 6 cuotas de {product.price?.toFixed(2) / 12}</p>}
                    {product.isFreeShipping && <span className="text-success fw-medium">Envío gratis</span>}
                </div>
                <div className="d-flex">
                    <button
                        className="p-0 border border-0 bg-transparent text-primary me-4"
                        onClick={(e) => {
                            e.stopPropagation();
                            addToCart(product, 1)
                        }}
                    >Agregar al carrito</button>
                    <button
                        className="p-0 border border-0 bg-transparent text-primary"
                        onClick={(e) => {
                            e.stopPropagation();
                            getToken().then(token => {
                                toggleFavorite(token, product.id)
                            })
                        }}
                    >Eliminar</button>
                </div>
            </div>
        </div>
    )
}