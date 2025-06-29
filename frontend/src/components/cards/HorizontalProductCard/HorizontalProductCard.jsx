import { useCartContext } from "../../../context/CartContext"
import { toggleFavorite } from "../../../services/ApiService";
import { getToken } from "../../../services/TokenService";

export default function HorizontalProductCard({ product, showOptions = true }) {
    const { addToCart } = useCartContext();

    const handleFavoriteToggle = (e) => {
        e.stopPropagation();
        getToken().then(token => {
            toggleFavorite(token, product.id)
        })
    }

    return (
        <div className="p-2 pb-4 d-flex border-bottom justify-content-between">
            <a href={`/product/${product.id}`} className="mx-auto">
                <img className="img-fluid mx-auto" src={product.pictures[0]} style={{ maxHeight: "180px" }} />
            </a>
            <div className="d-flex px-2 w-75 flex-column justify-content-between">
                <div>
                    <a href={`/product/${product.id}`} className="fs-4 fw-medium text-decoration-none text-black">{product.name}</a>
                    {product.priceDiscountPercentage && <span className="text-decoration-line-through fw-light">${product.price?.toFixed(2)}</span>}
                    {product.priceDiscountPercentage ?
                        <h5>$ {product.price?.toFixed(2) * (product.priceDiscountPercentage / 100)}<span className="text-success fw-light"> {product.priceDiscountPercentage}% OFF</span></h5>
                        :
                        product.price && <h5>$ {product.price?.toFixed(2)}</h5>
                    }
                    {product.price && <p className="fw-light">en 6 cuotas de {product.price?.toFixed(2) / 12}</p>}
                    {product.isFreeShipping && <span className="text-success fw-medium">Envío gratis</span>}
                </div>
                {
                    showOptions &&
                    <div className="d-flex">
                        <button
                            className="p-0 border border-0 bg-transparent text-primary me-4"
                            onClick={() => {
                                addToCart(product, 1)
                            }}
                        >Agregar al carrito</button>
                        <button
                            className="p-0 border border-0 bg-transparent text-primary"
                            onClick={handleFavoriteToggle}
                        >Eliminar</button>
                    </div>
                }
            </div>
        </div>
    )
}