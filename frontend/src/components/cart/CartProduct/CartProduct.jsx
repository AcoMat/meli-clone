
import { memo, useState } from 'react';
import { useCartContext } from '../../../context/CartContext';

const CartProduct = memo(({ cartItem }) => {
    const { addProductToCart, removeProductFromCart } = useCartContext();
    const [quantity, setQuantity] = useState(cartItem.amount);

    const increaseQuantity = () => {
        addProductToCart(cartItem.product.id, quantity + 1);
        setQuantity(quantity + 1);
    };

    const decreaseQuantity = () => {
        addProductToCart(cartItem.product.id, quantity > 1 ? quantity - 1 : 1);
        setQuantity(quantity > 1 ? quantity - 1 : 1);
    }


    return (
        <div className='bg-body rounded'>
            <div className='d-flex justify-content-between p-4 mb-3'>
                <div className='d-flex align-items-center'>
                    <img src={cartItem.product.images[0]} className='me-4' style={{ maxHeight: "100px" }} />
                    <div className='d-flex flex-column'>
                        <span className='fs-5 fw-medium mb-2'>{cartItem.product.title}</span>
                        <a onClick={() => removeProductFromCart(cartItem.product.id)} className='text-primary text-decoration-none mb-2' style={{ cursor: "pointer" }}>Eliminar</a>
                        <div
                            className="d-inline-flex justify-content-center align-items-center gap-3 border rounded p-1"
                            style={{ width: "fit-content" }}
                        >
                            <button className={`fs-5 border border-0 bg-transparent ${quantity > 1 && 'text-primary'}`} onClick={decreaseQuantity}>-</button>
                            <span>{quantity}</span>
                            <button className='fs-5 border border-0 bg-transparent text-primary' onClick={increaseQuantity}>+</button>
                        </div>

                    </div>
                </div>
                <div className='d-flex align-items-center'>
                    <span className='fw-medium fs-4'>$ {(cartItem.product.price * quantity).toFixed(2)}</span>
                </div>
            </div>
            <div className='d-flex justify-content-between py-3 border-top px-4'>
                <span>Envío</span>
                {
                    cartItem.product.shipping.price === 0 ?
                        <span className='text-success fw-medium'>Gratis</span>
                        :
                        <span className='fw-medium'>$ {cartItem.product.shipping.price.toFixed(2)}</span>
                }
            </div>
        </div>
    )
});

export default CartProduct;