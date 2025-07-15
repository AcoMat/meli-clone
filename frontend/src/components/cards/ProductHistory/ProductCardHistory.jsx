import { Link, useNavigate } from "react-router-dom"
import { formatARS } from "../../../util/priceUtil"

export default function ProductCardHistory({ product, amount, showReviewLink = false }) {
    let navigate = useNavigate()

    return (
        <div className="py-2 pb-4 d-flex border-bottom" onClick={() => navigate(`/product/${product.id}`)} style={{ cursor: "pointer" }}>
            <img className="img-fluid border rounded me-3" src={product.pictures[0]} style={{ maxHeight: "100px" }} alt="product image"/>
            <div className="d-flex px-2 w-75 flex-column justify-content-between">
                <div>
                    <h4 className="fs-4 text-break"><span className="text-secondary">{amount}x </span>{product.name}</h4>
                    {product.discount && <span className="text-decoration-line-through fw-light">{formatARS(product?.price)}</span>}
                    {product.priceDiscountPercentage ?
                        <h5>{formatARS(product.priceWithDiscountApplied)}<span className="text-success fw-light"> {product.priceDiscountPercentage}% OFF</span></h5>
                        :
                        product.price && <h5>$ {product.price?.toFixed(2)}</h5>
                    }
                    {showReviewLink && (
                        <Link to={`/product/${product.id}/review`} onClick={(e) => e.stopPropagation()}>
                            Calificar
                        </Link>
                    )}
                </div>
            </div>
        </div>
    )
}