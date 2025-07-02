import LargeBlueButton from '../../basic/LargeBlueButton/LargeBlueButton';
import QuantitySelector from '../../basic/AmountSelector/AmountSelector';
import { useNavigate } from 'react-router-dom';
import LikeStarSwitch from '../../basic/LikeStarSwitch/LikeStarSwitch';
import SecundaryBtn from '../../basic/SecundaryBtn/SecundaryBtn';
import { useCartContext } from '../../../context/CartContext';
import { useState } from 'react';
import Toast from 'bootstrap/js/dist/toast';
import { useUserContext } from '../../../context/UserContext';
import { formatARS } from '../../../util/priceUtil';

function ProductBuy({ product }) {
    const { user } = useUserContext();
    const [amount, setAmount] = useState(1);
    const navigate = useNavigate();

    const { addToCart } = useCartContext();

    const handleAdd = (redirect = false) => {
        if (!user) { navigate("/login"); return }
        addToCart(product, amount);
        showToast();
        if (redirect) navigate("/cart");
    }

    const showToast = () => {
        const toastLiveExample = document.getElementById('cart-toast');
        const toast = Toast.getOrCreateInstance(toastLiveExample);
        toast.show();
    }

    return (
        <>
            <div className="col bg-body" style={{ minHeight: 0, maxWidth: "400px" }}>
                <div className="position-relative" style={{ minWidth: 0 }}>
                    <LikeStarSwitch productId={product?.id} />
                    <h6 className="fs-4 w-75 fw-semibold text-break">{product?.name}</h6>
                    <span style={{ color: "#666666", fontSize: "13px" }} onClick={() => { }}>
                        Por {product?.owner?.name || "???"}
                    </span>
                    {
                        product?.priceDiscountPercentage ?
                            <h5 className='mb-0 mt-2'>
                                <span className="text-decoration-line-through fw-light">{formatARS(product?.price)}</span>
                                <p>
                                    <span className="fs-2 fw-normal"> {formatARS(product?.priceWithDiscountApplied)}</span>
                                    <span className="text-success fw-medium"> {product?.priceDiscountPercentage}% OFF</span>
                                </p>
                            </h5>
                            :
                            <h5 className='mb-0 mt-2'>{formatARS(product?.price)}</h5>
                    }
                    <p style={{ color: "#00A650" }}>En 12 cuotas de {product?.price ? formatARS(product?.price / 12) : "???"}</p>
                    <p className='text-success mb-3 fw-medium'>{product?.isFreeShipping ? "Envío gratis" : ` ${formatARS(product?.shipping?.price)}`}</p>
                    <h6>Stock disponible</h6>
                    <div style={{ minWidth: 0, overflow: 'hidden', width: '100%' }}>
                        <QuantitySelector amount={amount} setAmount={setAmount} />
                    </div>
                </div>
                <div className="w-100 mt-2 gap-2 d-flex flex-column">
                    <LargeBlueButton onClick={() => handleAdd(true)} text={"Comprar ahora"} />
                    <SecundaryBtn onClick={() => handleAdd()} text={"Agregar al carrito"} />
                </div>
            </div>
            <div className='toast-container position-fixed bottom-0 end-0 p-3'>
                <div id='cart-toast' className="toast align-items-center" role="alert" aria-live="assertive" aria-atomic="true">
                    <div className="d-flex bg-success-subtle">
                        <div className="toast-body text-success">
                            <strong>Producto agregado al carrito.</strong>
                        </div>
                        <button type="button" className="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ProductBuy;