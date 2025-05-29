import { useNavigate } from 'react-router-dom';
import LargeBlueButton from '../../basic/LargeBlueButton/LargeBlueButton.jsx';

function CartSumary({ cartItems }) {
    let navigate = useNavigate();

    const totalProducts = cartItems?.reduce(
        (total, item) => total + (item.amount ?? 0),
        0
    );
    const totalShippings = cartItems?.reduce(
        (total, item) => total + (item.product?.isFreeShipping ? 0 : 1),
        0
    );
    const totalProductsPrice = cartItems?.reduce(
        (total, item) => total + ((item.amount ?? 0) * (item.product?.price ?? 0)),
        0
    );
    const totalShippingsPrice = cartItems?.reduce(
        (total, item) => total + (item.product?.shipping?.price ?? 0),
        0
    );

    return (
        cartItems?.length < 1 || !cartItems ?

            <div className='d-flex flex-column rounded bg-body p-4 bg-opacity-50'>
                <h3 className='fs-5 pb-4 border-bottom opacity-50'>Resumen de compra</h3>
                <span className='opacity-50'>Aquí verás los importes de tu compra una vez que agregues productos.</span>
            </div>
            :
            <div className='d-flex flex-column rounded bg-body p-4 sticky-top w-100' style={{ top: "30px" }}>
                <h3 className='fs-5 pb-4 border-bottom'>Resumen de compra</h3>
                <div className='d-flex justify-content-between'>
                    <p>Productos ({totalProducts})</p>
                    <p>$ {totalProductsPrice.toFixed(2)}</p>
                </div>
                <div className='d-flex justify-content-between'>
                    <p>Envios a pagar ({totalShippings})</p>
                    <p>$ {totalShippingsPrice.toFixed(2)}</p>
                </div>
                <div className='d-flex justify-content-between'>
                    <p className="fs-5 fw-semibold">Total</p>
                    <p className="fs-5 fw-semibold"> $ {(totalProductsPrice + totalShippingsPrice).toFixed(2)}</p>
                </div>
                {

                }
                <LargeBlueButton text="Continuar compra" onClick={() => navigate("/checkout")} />
            </div>
    );
}

export default CartSumary;

