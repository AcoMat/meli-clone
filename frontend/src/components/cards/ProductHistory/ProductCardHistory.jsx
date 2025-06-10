import { useNavigate } from "react-router-dom"
import BlueButton from "../../basic/BlueButton/BlueButton"

export default function ProductCardHistory({ product }) {
    let navigate = useNavigate()

    return (
        <div className="py-2 pb-4 d-flex border-bottom" onClick={() => navigate(`/product/${product.id}`)} style={{ cursor: "pointer" }}>
            <img className="img-fluid border rounded me-3" src={product.pictures[0]} style={{ maxHeight: "100px" }} />
            <div className="d-flex px-2 w-75 flex-column justify-content-between">
                <div>
                    <h4 className="fs-4 text-break">{product.name}</h4>
                    {product.discount && <span className="text-decoration-line-through fw-light">${product.price.toFixed(2)}</span>}
                    {product.priceDiscountPercentage ?
                        <h5>$ {product.price.toFixed(2) * (product.priceDiscountPercentage / 100)}<span className="text-success fw-light"> {product.priceDiscountPercentage}% OFF</span></h5>
                        :
                        product.price && <h5>$ {product.price?.toFixed(2)}</h5>
                    }
                    <a href={`/product/${product.id}/review`}>Calificar</a>
                </div>
            </div>
        </div>
    )
}