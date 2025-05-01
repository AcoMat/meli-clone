import { useRef, useState } from "react";
import LargeBlueButton from "../../basic/LargeBlueButton/LargeBlueButton";

export default function PasswordStep({ setFormData, nextStage }) {
    const passRef = useRef(null);
    const confirmPassRef = useRef(null);
    const [error, setError] = useState(null);

    const validateForm = () => {
        if (passRef.current.value.trim() === '') {
            setError("Por favor, ingrese una contraseña");
        } else if (passRef.current.value !== confirmPassRef.current.value) {
            setError("Las contraseñas no coinciden");
        } else {
            setFormData((prevData) => ({
                ...prevData,
                password: passRef.current.value
            }));
            nextStage();
        }
    }

    return (
        <div className='p-5 bg-body pass-step rounded mx-auto'>
            <h3>Crea una contraseña</h3>
            <p>Ingresá una contraseña segura que no uses en otras plataformas</p>
            <div className='d-flex flex-column gap-3'>
                <label>
                    Ingresá tu contraseña
                </label>
                <input ref={passRef} name='password' type='password' onChange={() => {setError(null)}} className={`${error ? 'error' : ''}`} />
            </div>
            <div className='d-flex flex-column gap-3'>
                <label>
                    Confirmá tu contraseña
                </label>
                <input ref={confirmPassRef} name='confirmPassword' onChange={() => {setError(null)}} type='password' className={`${error ? 'error' : ''}`} />
            </div>
            {error && <label className='error mt-3'>{error}</label>}
            <div className='mt-4'>
                <LargeBlueButton text='Continuar' onClick={validateForm} />
            </div>
        </div>
    )
}