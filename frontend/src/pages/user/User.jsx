import UserProfile from "../../components/user/UserProfile/UserProfile";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useUserContext } from "../../context/UserContext";
import LoadingSwitch from "../../components/basic/LoadingSwitch/LoadingSwitch";

function User() {
    const { user, loading } = useUserContext();
    let navigate = useNavigate();

    useEffect(()=> {
        if(!loading && !user) {
            navigate('/');
        }
    },[user, loading])

    return (
        <LoadingSwitch loading={loading}>
            <UserProfile user={user} />
        </LoadingSwitch>
    );
}

export default User;