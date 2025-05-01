import { useUserContext } from "../context/AuthContext";
import UserProfile from "../components/user/UserProfile/UserProfile";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function User() {
    const { user, error, loading } = useUserContext();
    let navigate = useNavigate();

    useEffect(()=> {
        if(loading){return}
        if(!user || error) {
            navigate('/');
        }
    },[user])


    return (
        <UserProfile user={user} />
    );
}

export default User;