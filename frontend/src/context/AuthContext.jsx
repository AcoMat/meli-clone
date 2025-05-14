import { createContext, useContext, useEffect, useState } from 'react';
import { authLogin, getUser, likeProduct, userRegister } from '../services/AxiosService';
import { clearToken, getToken, setToken } from '../services/TokenService';

export const UserContext = createContext()

export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        update();
    }, [])
    
    const login = async (email, password) => {
        setLoading(true);
        setError(null);
        try {
            const user = await authLogin(email, password);
            setUser(user);
        } catch (err) {
            setError(err.response.data.title);
        } finally {
            setLoading(false)
        }
    }
    
    const register = async (form) => {
        setLoading(true);
        setError(null);
        try {
            const user = await userRegister(form);
            setUser(user);
        } catch (err) {
            setError(err.response.data.title);
        } finally {
            setLoading(false)
        }
    }
    
    const logout = () => {
        setUser(null);
        clearToken();
    };
    
    const like = async (productId) => {
        try {
            const user = await likeProduct(productId);
            setUser(user);
        } catch (err) {
            console.log(err.message);
        }
    }

    const update = async () => {
        setLoading(true);
        setError(null);
        getUser()
        .then(user => setUser(user))
        .then(setLoading(false))
        .catch(err => {console.log(err.response.data.title);setError(err.response.data.title);logout()})
    }

    return (
        <UserContext.Provider value={{ user, login, register, logout, like, update, loading, error }}>
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = () => useContext(UserContext);