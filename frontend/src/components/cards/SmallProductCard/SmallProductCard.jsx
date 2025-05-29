import './SmallProductCard.css'

const SmallProductCard = ({ product, clickfnc, md }) => {
    return (
        <div className={`sm-product-card-wrapper bg-body`} onClick={() => clickfnc(`/product/${product.id}`)}>
            <div className={`card-img-container${!md ? '-sm' : '-md'}`}>
                <img className='' src={product.images[0]} alt="product image"/>
            </div>
            <div className="sm-product-info">
                <span className='title text-break'>{product.title}</span>
                <span className={`price${!md ? '-sm' : '-md'}`}>$ {product.price.toFixed(2)}</span>
                <span className='cuotas'>En 12 cuotas de ${(product.price / 12).toFixed(2)}</span>
                <span className={`sm-freeShip ${product.shipping.price == 0 ? 'visible' : 'hidden'}`}>Envío gratis</span>
            </div>
        </div>
    )
}

export default SmallProductCard
