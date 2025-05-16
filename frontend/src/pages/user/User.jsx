import UserProfile from "../../components/user/UserProfile/UserProfile";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../../context/UserContext";

function User() {
    const { user, loading } = useUserContext();
    let navigate = useNavigate();

    useEffect(()=> {
        if(!loading && !user) {
            navigate('/');
        }
    },[user, loading])

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <UserProfile user={user} />
    );
}

export default User;