import { useState } from 'react';
import nextArrow from '../../../assets/arrows/next-arrow.svg'
import downArrow from '../../../assets/arrows/down-line.svg'
import LargeBlueButton from '../../basic/LargeBlueButton/LargeBlueButton';
import { useUserContext } from '../../../context/UserContext';
import profileImagePlaceholder from '../../../assets/ui/profile-placeholder.png';

function UserProfile({user}) {
    const { logout } = useUserContext();

    const [expandedSections, setExpandedSections] = useState({
        personalInfo: false,
        accountDetails: false,
        security: false
    });

    const toggleSection = (section) => {
        setExpandedSections(prev => ({
            ...prev,
            [section]: !prev[section]
        }));
    }

    return (
            <div className='content-wrapper'>
                <div className='w-75 px-md-5 mx-auto my-5 d-flex flex-column gap-4'>
                    <div className='bg-body d-flex p-4 rounded shadow-sm'>
                        <img className="rounded-circle object-fit-cover" src={user?.image || profileImagePlaceholder} alt="User" width={80} height={80} />
                        <div className='d-flex flex-column mx-4 pt-2'>
                            <span className='fs-5 fw-medium'>{user?.firstName + ' ' + user?.lastName}</span>
                            <p>{user?.email}</p>
                        </div>
                    </div>
                    <div className='bg-body d-flex flex-column gap-4 p-4 rounded shadow-sm '>
                        <div>
                            <button 
                                className='w-100 d-flex bg-transparent border border-0 text-start position-relative' 
                                data-bs-toggle="collapse" 
                                data-bs-target="#personal-info"
                                onClick={() => toggleSection('personalInfo')}>
                                <img className='p-3' src="https://www.mercadolibre.com/org-img/mkt/email-mkt-assets/my-profile/ML/myInformation.svg" alt="User" width={55} height={55} />
                                <div className='d-flex flex-column mx-2'>
                                    <span className='fs-6'>Información personal</span>
                                    <p className='fw-light'>Información de tu identidad y actividad fiscal.</p>
                                </div>
                                <img className='position-absolute top-50 end-0 translate-middle-y' src={expandedSections.personalInfo ? downArrow : nextArrow} width={20} height={20} />
                            </button>
                            <div className="collapse" id="personal-info">
                                <div className="px-4 pt-0">
                                    <li>Nombre: <span className='fw-light'>{user?.firstName + ' ' + user?.lastName}</span></li>
                                    <li>Condición fiscal: <span className='fw-light'>Consumidor final</span></li>
                                </div>
                            </div>
                        </div>
                        <div>
                            <button 
                                className='w-100 d-flex bg-transparent border border-0 text-start position-relative' 
                                data-bs-toggle="collapse" 
                                data-bs-target="#account-details"
                                onClick={() => toggleSection('accountDetails')}>
                                <img className='p-3' src="https://www.mercadolibre.com/org-img/mkt/email-mkt-assets/my-profile/ML/profileIcon.svg" alt="User" width={55} height={55} />
                                <div className='d-flex flex-column mx-2'>
                                    <span className='fs-6'>Datos de tu cuenta</span>
                                    <p className='fw-light'>Datos que representan a la cuenta en Mercado Libre y Mercado Pago.</p>
                                </div>
                                <img className='position-absolute top-50 end-0 translate-middle-y' src={expandedSections.accountDetails ? downArrow : nextArrow} width={20} height={20} />
                            </button>
                            <div className="collapse" id="account-details">
                                <div className="px-4 pt-0">
                                    <li>E-mail: <span className='fw-light'>{user?.email}</span></li>
                                </div>
                            </div>
                        </div>
                        <div>
                            <button 
                                className='w-100 d-flex bg-transparent border border-0 text-start position-relative' 
                                data-bs-toggle="collapse" 
                                data-bs-target="#security"
                                onClick={() => toggleSection('security')}>
                                <img className='p-3' src="https://http2.mlstatic.com/storage/security-settings-middle-end/security-hub-img/security/icon-security-ml.svg?i=3.0.21" alt="User" width={55} height={55} />
                                <div className='d-flex flex-column mx-2'>
                                    <span className='fs-6'>Seguridad</span>
                                    <p className='fw-light'>Configuracion relacionada con la seguridad de tu cuenta.</p>
                                </div>
                                <img className='position-absolute top-50 end-0 translate-middle-y' src={expandedSections.security ? downArrow : nextArrow} width={20} height={20} />
                            </button>
                            <div className="collapse" id="security">
                                <div className="px-4 pt-0">
                                    <li>Contraseña: <span className='fw-light'>********</span></li>
                                </div>
                            </div>
                        </div>
                    </div>
                    <LargeBlueButton text='Cerrar sesión' onClick={() => {logout()}} />
                </div>
            </div>
    );
}

export default UserProfile;