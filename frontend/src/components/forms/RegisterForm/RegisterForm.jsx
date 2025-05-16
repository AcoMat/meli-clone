import { useEffect, useState } from 'react';
import './RegisterForm.css'
import checklist from '../../../assets/ui/check-list.svg'
import loadingsvg from '../../../assets/ui/loading.svg'
import SecundaryBtn from '../../basic/SecundaryBtn/SecundaryBtn.jsx';
import { useNavigate } from 'react-router-dom';
import EmailStep from './EmailStep.jsx';
import NameStep from './NameStep.jsx';
import PasswordStep from './PasswordStep.jsx';
import ImageStep from './ImageStep.jsx';
import { useUserContext } from '../../../context/UserContext.jsx';
import UpsForm from '../UpsForm.jsx';

function RegisterForm() {
    const navigate = useNavigate();
    const { user, register, error } = useUserContext();

    const [formData, setFormData] = useState({
        name: '',
        email: '',
        eula: false,
        password: '',
        confirmPassword: '',
        image: ''
    });

    const registerStages = {
        email: 'email',
        password: 'password',
        name: 'name',
        imageurl: 'imageurl',
        creating: 'creating',
        created: 'created',
        error: 'error'
    }

    const registerUser = async () => {
        setCurrentStage(registerStages.creating);
        delete formData.confirmPassword;
        delete formData.eula;
        await register(formData);
    }

    useEffect(() => {
        if (!error && user) {
            setCurrentStage(registerStages.created);
            setTimeout(() => {
                navigate("/")
                window.location.reload();
            }, 2000);
        } else if (error){
            setCurrentStage(registerStages.error);
        }
    }, [user, error])

    const [currentStage, setCurrentStage] = useState(registerStages.email);
    const nextStage = async () => {
        switch (currentStage) {
            case registerStages.email:
                setCurrentStage(registerStages.name);
                break;
            case registerStages.name:
                setCurrentStage(registerStages.password);
                break;
            case registerStages.password:
                setCurrentStage(registerStages.imageurl);
                break;
            case registerStages.imageurl:
                registerUser();
                break;
            default:
                setCurrentStage(registerStages.email);
        }
    }

    return (
        <div className='content-wrapper mx-auto register-form'>
            {
                currentStage === registerStages.email &&
                <EmailStep setFormData={setFormData} nextStage={nextStage} />
                || currentStage === registerStages.name &&
                <NameStep setFormData={setFormData} nextStage={nextStage} />
                || currentStage === registerStages.password &&
                <PasswordStep setFormData={setFormData} nextStage={nextStage} />
                || currentStage === registerStages.imageurl &&
                <ImageStep setFormData={setFormData} nextStage={nextStage} />
                || currentStage === registerStages.creating &&
                <div className='w-50 p-5 bg-body rounded mx-auto d-flex justify-content-center flex-column gap-4 text-center'>
                    <img className='mx-auto w-25 ml-blue-filter' src={loadingsvg} />
                    <h4>Procesando tus datos...</h4>
                </div>
                || currentStage === registerStages.created &&
                <div className='w-50 p-5 bg-body rounded mx-auto d-flex justify-content-center flex-column gap-4 text-center'>
                    <img src={checklist} className='ml-blue-filter w-50 mx-auto' />
                    <h3>¡Listo {formData.name}! Ya tenés tu cuenta</h3>
                </div>
                || currentStage === registerStages.error &&
                <UpsForm error={error} nextStage={nextStage} />
            }
        </div>
    )
};

export default RegisterForm;