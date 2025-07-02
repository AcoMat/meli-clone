import { formatARS } from '../../../util/priceUtil'
import LikeStarSwitch from '../../basic/LikeStarSwitch/LikeStarSwitch'
import './ProductCard.css'

const ProductCard = ({ product, clickfnc}) => {

    return (
        <div className="product-card-wrapper container border-0 position-relative bg-body rounded px-0 fadein" onClick={() => clickfnc(`/product/${product.id}`)}>
            <LikeStarSwitch productId={product.id}/>
            <div className='card-preview-container'>
                <img src={product.pictures[0]} alt="product image" className='img-fluid' />
            </div>
            <div className="p-3 product-card-info-wrapper">
                <h6 className='text-break'>{product.name}</h6>
                <h5 className='mb-0'>{formatARS(product.price)}</h5>
                {product.price && <p style={{ color: "#00A650", }}>En 12 cuotas de {formatARS((product.price / 12).toFixed(2))}</p>}
                {product.isFreeShipping && <span className='freeShip'>Llega gratis</span>}
            </div>
        </div>
    )
}

export default ProductCard
