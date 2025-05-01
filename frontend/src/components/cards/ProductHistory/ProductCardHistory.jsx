import { useNavigate } from "react-router-dom"

export default function ProductCardHistory({ product }) {
    let navigate = useNavigate()

    return (
        <div className="py-2 pb-4 d-flex border-bottom" onClick={() => navigate(`/product/${product.id}`)} style={{ cursor: "pointer" }}>
            <img className="img-fluid border rounded me-3" src={product.images[0]} style={{ maxHeight: "100px" }} />
            <div className="d-flex px-2 w-75 flex-column justify-content-between">
                <div>
                    <h4 className="fs-4 text-break">{product.title}</h4>
                    {product.discount && <span className="text-decoration-line-through fw-light">${product.price.toFixed(2)}</span>}
                    {product.discount ?
                        <h5>$ {product.price.toFixed(2) * (product.discount / 100)}<span className="text-success fw-light"> {product.discount}% OFF</span></h5>
                        :
                        <h5>$ {product.price.toFixed(2)}</h5>
                    }
                </div>
            </div>
        </div>
    )
}