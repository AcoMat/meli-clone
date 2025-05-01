import { useNavigate } from 'react-router-dom';
import { useCartContext } from '../context/CartContext';
import { useUserContext } from '../context/AuthContext';
import loadingsvg from '../assets/ui/loading.svg';
import checklist from '../assets/ui/check-list.svg';
import ChoosePaymentStage from '../components/forms/PurchaseForm/ChoosePaymentStage';
import CardDetailsStage from '../components/forms/PurchaseForm/CardDetailsStage';
import { useEffect, useState } from 'react';
import UpsForm from '../components/forms/UpsForm';
import CartSumary from '../components/cart/CartSumary/CartSumary';
import PurchaseSumary from '../components/forms/PurchaseForm/PurchaseSumary/PurchaseSumary';
import BlueButton from '../components/basic/BlueButton/BlueButton';

export default function Checkout() {
    const navigate = useNavigate();
    const { cart, purchaseCart, error, loading } = useCartContext();
    const { user } = useUserContext();

    const [formData, setFormData] = useState({
        name: "",
        cardNumber: "",
        cvv: "",
        expirationDate: "",
    });

    useEffect(() => {
        console.log(formData);

    }, [formData])

    const formStages = {
        choosePayment: 0,
        cardDetails: 1,
        purchasing: 2,
        purchased: 3,
        error: -1,
    }

    const confirmPurchase = () => {
        setCurrentStage(formStages.purchasing);
        purchaseCart(formData);
    }

    const [currentStage, setCurrentStage] = useState(formStages.choosePayment);
    const nextStage = async () => {
        switch (currentStage) {
            case formStages.choosePayment:
                setCurrentStage(formStages.cardDetails);
                break;
            case formStages.cardDetails:
                setCurrentStage(formStages.purchasing);
                delete formData.cardType;
                confirmPurchase();
                break;
            default:
                setCurrentStage(formStages.choosePayment);
                setFormData({
                    name: "",
                    cardNumber: "",
                    cvv: "",
                    expirationDate: "",
                });
                break;
        }
    }

    useEffect(() => {
        if (currentStage === formStages.purchasing) {
            console.log(currentStage);
            if (!error && !loading) {
                setCurrentStage(formStages.purchased);
                setTimeout(() => {
                    navigate("/purchases")
                    window.location.reload();
                }, 2000);
            } else if (error && !loading) {
                setCurrentStage(formStages.error);
            }
        }
    }, [user, error])

    return (
        <div className='content-wrapper'>
            {currentStage === formStages.choosePayment &&
                <div className='d-flex flex-column flex-md-row gap-4'>
                    <div className='flex-grow-1'>
                        <ChoosePaymentStage setFormData={setFormData} nextStage={nextStage} />
                    </div>
                    <PurchaseSumary cartItems={cart?.items} />
                </div>
                || currentStage === formStages.cardDetails &&
                <div className='d-flex flex-column flex-md-row gap-4'>
                    <div className='flex-grow-1'>
                        <CardDetailsStage setFormData={setFormData} formData={formData} nextStage={nextStage} />
                    </div>
                    <div>
                        <PurchaseSumary cartItems={cart?.items} />
                    </div>
                </div>
                || currentStage === formStages.purchasing &&
                <div className='w-50 p-5 bg-body rounded mx-auto d-flex justify-content-center flex-column gap-4 text-center my-4'>
                    <img className='mx-auto w-25 ml-blue-filter' src={loadingsvg} />
                    <h4>Procesando tus datos...</h4>
                </div>
                || currentStage === formStages.purchased &&
                <div className='w-50 p-5 bg-body rounded mx-auto d-flex justify-content-center flex-column gap-4 text-center my-4'>
                    <img src={checklist} className='ml-blue-filter w-50 mx-auto' />
                    <h3>¡Listo {formData.name}! Tu compra se realizó con exito.</h3>
                </div>
                || currentStage === formStages.error &&
                <UpsForm error={error} nextStage={nextStage} />
            }
        </div>
    );
}