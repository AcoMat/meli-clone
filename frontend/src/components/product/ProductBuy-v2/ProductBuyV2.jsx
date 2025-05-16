import LargeBlueButton from '../../basic/LargeBlueButton/LargeBlueButton';
import QuantitySelector from '../../basic/AmountSelector/AmountSelector';
import { useNavigate } from 'react-router-dom';
import LikeStarSwitch from '../../basic/LikeStarSwitch/LikeStarSwitch';
import SecundaryBtn from '../../basic/SecundaryBtn/SecundaryBtn';
import { useCartContext } from '../../../context/CartContext';
import { useState } from 'react';
import Toast from 'bootstrap/js/dist/toast';
import { useUserContext } from '../../../context/UserContext';

function ProductBuyV2({ product }) {
    const { user } = useUserContext();
    const [amount, setAmount] = useState("1");
    const navigate = useNavigate();

    const { addProductToCart } = useCartContext();

    const handleAdd = (now) => {
        if (!user) { navigate("/login"); return }
        addProductToCart(product.id, amount)
        if (now) { navigate("/cart") }
        showToast(); // Show toast message
    }

    const showToast = () => {
        const toastLiveExample = document.getElementById('cart-toast');
        const toast = Toast.getOrCreateInstance(toastLiveExample);
        toast.show();
    }

    return (
        <>
            <div className="col-4 bg-body">
                <div className="position-relative" >
                    <LikeStarSwitch productId={product?.id} />
                    <h6 className="w-75 text-break">{product?.name}</h6>
                    <span style={{ color: "#666666", fontSize: "13px" }} onClick={() => { }}>
                        Por {product?.owner?.name || "???"}
                    </span>
                    <h5 className='mb-0 mt-2'>$ {product?.price?.toFixed(2)}</h5>
                    <p style={{ color: "#00A650" }}>En 12 cuotas de ${(product?.price / 12).toFixed(2)}</p>
                    <p style={{ marginBottom: "12px" }}>Envio: $ {product?.shipping?.price?.toFixed(2)}</p>
                    <h6>Stock disponible</h6>
                    <QuantitySelector amount={amount} setAmount={setAmount} />
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

export default ProductBuyV2;