import CartItems from '../../components/cart/CartItems/CartItems';
import CartSumary from '../../components/cart/CartSumary/CartSumary';

function Cart() {
    return (
        <div className='content-wrapper my-4 d-flex justify-content-center align-items-center'>
            <div className='row w-100'>
                <div className='col-md-8 col-12 mb-3'>
                    <CartItems products={cart?.items} />
                </div>
                <div className='col-md-4 col-12 mb-3'>
                    <CartSumary cartItems={cart?.items} />
                </div>
            </div>
        </div>
    )
}

export default Cart;