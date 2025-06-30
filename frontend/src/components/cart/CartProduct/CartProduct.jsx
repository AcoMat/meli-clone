
import { memo, useState } from 'react';
import { useCartContext } from '../../../context/CartContext';
import LoadingSwitch from '../../basic/LoadingSwitch/LoadingSwitch';
import useGetProduct from '../../../hooks/useGetProduct';

const CartProduct = memo(({ cartItem }) => {
    const { updateAmount, removeFromCart } = useCartContext();
    const { product, loading } = useGetProduct(cartItem.product.id);
    const [amount, setAmount] = useState(cartItem.amount);

    const increaseAmount = () => {
        updateAmount(cartItem.product.id, amount + 1);
        setAmount(amount + 1);
    };

    const decreaseAmount = () => {
        updateAmount(cartItem.product.id, amount > 1 ? amount - 1 : 1);
        setAmount(amount > 1 ? amount - 1 : 1);
    }


    return (
        <LoadingSwitch loading={loading}>
            <div className='bg-body rounded'>
                <div className='d-flex justify-content-between p-4 mb-3'>
                    <div className='d-flex align-items-center'>
                        <img src={product?.pictures[0]} className='me-4' style={{ maxHeight: "100px" }} />
                        <div className='d-flex flex-column'>
                            <a href={`/product/${cartItem.product.id}`} className='fs-5 fw-medium mb-2 text-decoration-none text-dark'>{product?.name}</a>
                            <a onClick={() => removeFromCart(cartItem.product.id)} className='text-primary text-decoration-none mb-2' style={{ cursor: "pointer" }}>Eliminar</a>
                            <div
                                className="d-inline-flex justify-content-center align-items-center gap-3 border rounded p-1"
                                style={{ width: "fit-content" }}
                            >
                                <button className={`fs-5 border border-0 bg-transparent ${amount > 1 && 'text-primary'}`} onClick={decreaseAmount}>-</button>
                                <span>{amount}</span>
                                <button className='fs-5 border border-0 bg-transparent text-primary' onClick={increaseAmount}>+</button>
                            </div>

                        </div>
                    </div>
                    <div className='d-flex align-items-center'>
                        {
                            product?.price &&
                            <span className='fw-medium fs-4'>$ {(product?.price * amount).toFixed(2)}</span>
                        }
                    </div>
                </div>
                <div className='d-flex justify-content-between py-3 border-top px-4'>
                    <span>Envío</span>
                    {
                        product?.isFreeShipping ?
                            <span className='text-success fw-medium'>Gratis</span>
                            :
                            <span className='fw-medium'>$ {product?.shipping?.price?.toFixed(2)}</span>
                    }
                </div>
            </div>
        </LoadingSwitch>
    )
});

export default CartProduct;