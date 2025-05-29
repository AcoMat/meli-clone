import { useEffect, useRef, useState } from "react";
import LargeBlueButton from "../../basic/LargeBlueButton/LargeBlueButton";
import avatarPlaceHolder from '../../../assets/ui/profile-placeholder.png';

export default function ImageStep({ setFormData, nextStage }) {
    const [imageUrl, setImageUrl] = useState("");
    const [error, setError] = useState(null);

    const validateForm = () => {
        if (!isValidImageUrl(imageUrl)) {
            setError("Por favor, ingrese una url de imagen válida");
        } else {
            nextStage();
        }
    }


    useEffect(() => {
        setError(null);
        if(imageUrl === '') {
            setFormData((prevData) => ({
                ...prevData,
                image: avatarPlaceHolder
            }));
        }else {
            setFormData((prevData) => ({
                ...prevData,
                image: imageUrl
            }));
        }
    }, [imageUrl]);

    const isValidImageUrl = (url) => {
        if(url === '') {
            return true;
        }
        // Basic URL validation followed by image extension check
        const urlPattern = /^(https?:\/\/)[a-z0-9-]+(\.[a-z0-9-]+)+(\/[-a-z0-9%_.~#+]*)*(\?[;&a-z0-9%_.~+=-]*)?(\#[-a-z0-9]*)?$/i;

        return urlPattern.test(url);
    }

    return (
        <div className='p-5 bg-body rounded w-50 mx-auto'>
            <h3>Subí tu foto</h3>
            <p>Ingresá la url de tu imagen de perfil, Verán tu foto todas las personas que interactúen contigo en Mercado Libre y Mercado Pago </p>
            <div className='d-flex flex-column gap-3'>
                <label>
                    URL de la imagen:
                </label>
                <input name='image' value={imageUrl} onChange={(e) => { setImageUrl(e.target.value) }} />
                {error && <label className='error'>{error}</label>}
                <img className='mx-auto object-fit-cover rounded-circle' width={192} height={192} src={imageUrl ? imageUrl : avatarPlaceHolder} />
            </div>
            <div className='mt-4'>
                <LargeBlueButton text='Continuar' onClick={validateForm} />
            </div>
        </div>
    )
}