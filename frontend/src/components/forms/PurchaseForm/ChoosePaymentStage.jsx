import BlueButton from "../../basic/BlueButton/BlueButton";
import creditcard from '../../../assets/checkout/credit-card.svg';
import debittcard from '../../../assets/checkout/debit-card.svg';

export default function ChoosePaymentStage({ setFormData, nextStage }) {

    const handleNext = () => {
        setFormData((prevData) => ({ ...prevData, cardType: document.querySelector('input[name="payment"]:checked').id }));
        nextStage();
    }


    return (
        <>
            <h2 className="mb-5 mt-4">Eligí tu metodo de pago</h2>
            <div className="d-flex flex-column gap-3">
                <div className="bg-body d-flex gap-4 p-3 rounded fs-4 shadow-sm">
                    <input className="shadow-none" type="radio" name="payment" id="debito" />
                    <img className="rounded-circle p-2 border" src={debittcard} />
                    <label className="pt-0 w-100" for="debito">Tarjeta de débito</label>
                </div>
                <div className="bg-body d-flex gap-4 p-3 rounded fs-4 shadow-sm">
                    <input className="shadow-none" type="radio" name="payment" id="credito" />
                    <img className="rounded-circle p-2 border" src={creditcard} />
                    <label className="pt-0 w-100" for="credito">Tarjeta de crédito</label>
                </div>
            </div>
            <div className="my-4 d-flex justify-content-end mb-4">
                <BlueButton text="Continuar" onClick={handleNext} />
            </div>
        </>
    )
}