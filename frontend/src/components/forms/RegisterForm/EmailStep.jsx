import { useRef, useState } from "react";
import LargeBlueButton from "../../basic/LargeBlueButton/LargeBlueButton";
import { emailRegex } from "../../../util/emailUtils";

export default function EmailStep({ setFormData, nextStage }) {
    const emailRef = useRef(null);
    const eulaRef = useRef(null);
    const [error, setError] = useState(
        {
            email: null,
            eula: null
        }
    );


    const validateForm = () => {
        if (emailRef.current.value.trim() === '' || !emailRegex.test(emailRef.current.value)) {
            setError((prevData) => ({
                ...prevData,
                email: 'Por favor, ingrese un email válido'
            }));
        } else if (eulaRef.current.checked === false) {
            setError((prevData) => ({
                ...prevData,
                eula: 'Por favor, acepte los términos y condiciones'
            }));
        } else {
            setFormData((prevData) => ({
                ...prevData,
                email: emailRef.current.value
            }));
            nextStage();
        }
    }


    return (
        <div className='email-step bg-body mx-auto rounded p-3 my-3 p-md-5'>
            <h5>Ingresá tu e-mail</h5>
            <input ref={emailRef} className={`w-100 ${error.email ? 'error' : ''}`} name='email' onChange={() => { setError((prevData) => ({ ...prevData, email: null })) }} />
            {error.email && <label className='error'>{error.email}</label>}
            <label>
                <input type='checkbox' ref={eulaRef} className={`me-2 ${error.eula ? 'error' : ''}`} onChange={() => { setError((prevData) => ({ ...prevData, eula: null })) }} />
                Acepto los Términos y condiciones y autorizo el uso de mis datos de acuerdo a la Declaración de privacidad.
            </label>
            {error.eula && <label className='error'>{error.eula}</label>}
            <div className='mt-4'>
                <LargeBlueButton text='Continuar' onClick={validateForm} />
            </div>
        </div>
    )
}