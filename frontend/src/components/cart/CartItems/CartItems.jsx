import CartProduct from '../CartProduct/CartProduct.jsx'
import emptyCart from '../../../assets/cart/emptycart.svg'

function CartItems({ cartItems }) {

    return (
        <div className='d-flex flex-column gap-4 '>
            {
                !cartItems || cartItems?.length < 1 ?
                    <div className='d-flex justify-content-between align-items-center bg-body bg-opacity-50 px-4 py-5'>
                        <div className='d-flex gap-4'>
                            <img src={emptyCart} />
                            <div className='d-flex flex-column'>
                                <span className='fs-5'>Agregá productos y conseguí envío gratis</span>
                                <span className='text-secondary'>Para obtener envío gratis sumá productos de un mismo vendedor.</span>
                            </div>
                        </div>
                        <div>
                            <a className='text-decoration-none' href="/">Descubrir productos</a>
                        </div>
                    </div>
                    :
                    cartItems?.sort((a, b) => {
                        const idA = a?.productId || '';
                        const idB = b?.productId || '';
                        return idA.localeCompare(idB);
                    }).map((cartItem) => (
                        <div key={cartItem.product.id}>
                            <CartProduct
                                cartItem={cartItem}
                            />
                        </div>
                    ))
            }
        </div>
    )
};

export default CartItems;