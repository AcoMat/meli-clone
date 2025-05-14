import LoginForm from "../components/forms/LoginForm/LoginForm";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../context/AuthContext";
import { useEffect } from "react";


function Login() {
    const { user, error } = useUserContext();
    let navigate = useNavigate();

    useEffect(()=> {
        if(user || error) {
            navigate('/');
        }
    },[])

    return (
        <LoginForm/>
    );
}

export default Login;