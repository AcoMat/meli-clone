import LikeStarSwitch from '../../basic/LikeStarSwitch/LikeStarSwitch'
import './ProductCard.css'

const ProductCard = ({ product, clickfnc}) => {

    const handleOwnerClick = (e) => {
        e.stopPropagation();
        clickfnc(`/user/${product.owner.id}`);
    }
    return (
        <div className="product-card-wrapper container border-0 position-relative bg-body rounded px-0 fadein" onClick={() => clickfnc(`/product/${product.id}`)}>
            <LikeStarSwitch productId={product.id}/>
            <div className='card-preview-container'>
                <img src={product.images[0]} alt="product image" className='img-fluid' />
            </div>
            <div className="p-3 product-card-info-wrapper">
                <h6 className='text-break'>{product.title}</h6>
                <p className="mb-1 product-card-owner" onClick={handleOwnerClick}>Por {product.owner.name}</p>
                <h5 className='mb-0'>$ {product.price.toFixed(2)}</h5>
                <p style={{ color: "#00A650", }}>En 12 cuotas de ${(product.price / 12).toFixed(2)}</p>
                {product.shipping.price == 0 && <span className='freeShip'>Llega gratis</span>}
            </div>
        </div>
    )
}

export default ProductCard
