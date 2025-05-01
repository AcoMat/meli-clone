import { useUserContext } from "../../../context/AuthContext";
import { useCartContext } from "../../../context/CartContext"

export default function HorizontalProductCard({ product, onClick}) {
    const { addProductToCart } = useCartContext();
    const { like } = useUserContext();

    return (
        <div className="p-2 pb-4 d-flex border-bottom" onClick={onClick} style={{ cursor: "pointer" }}>
            <img className="img-fluid" src={product.images[0]} style={{ maxHeight: "180px" }} />
            <div className="d-flex px-2 w-75 flex-column justify-content-between">
                <div>
                    <h4 className="fs-4 text-break">{product.title}</h4>
                    {product.discount && <span className="text-decoration-line-through fw-light">${product.price.toFixed(2)}</span>}
                    {product.discount ?
                        <h5>$ {product.price.toFixed(2) * (product.discount / 100)}<span className="text-success fw-light"> {product.discount}% OFF</span></h5>
                        :
                        <h5>$ {product.price.toFixed(2)}</h5>
                    }
                    <p className="fw-light">en 6 cuotas de {product.price.toFixed(2) / 12}</p>
                    {product.shipping.price == 0 && <span className="text-success fw-medium">Envío gratis</span>}
                </div>
                <div className="d-flex">
                    <button 
                        className="p-0 border border-0 bg-transparent text-primary me-4"
                        onClick={(e) => {
                            e.stopPropagation();
                            addProductToCart(product.id, 1)
                        }}
                    >Agregar al carrito</button>
                    <button 
                        className="p-0 border border-0 bg-transparent text-primary"
                        onClick={(e) => {
                            e.stopPropagation();
                            like(product.id)
                        }}
                    >Eliminar</button>
                </div>
            </div>
        </div>
    )
}