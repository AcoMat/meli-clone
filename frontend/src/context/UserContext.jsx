import { createContext, useContext, useEffect, useState } from 'react';
import { clearToken, getToken, setToken } from '../services/tokenService';
import { getUserProfile, login as loginService, register as registerService } from '../services/ApiService';

export const UserContext = createContext()

export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        setLoading(true);
        getToken().then((token) => {
            if (token && token !== '') {
                getUserProfile(token)
                    .then((userData) => {
                        if (userData) {
                            setUser(userData);
                        } else {
                            clearToken();
                        }
                        setLoading(false);
                    })
            } else {
                clearToken();
                setLoading(false);
            }
        });
    }, []);

    const register = async (firstname, lastname, email, password) => {
        const res = await registerService(firstname, lastname, email, password);
        if (res) {
            setToken(res.headers);
            setUser(res.data);
        } else {
            console.error('Registration failed');
        }
    }

    const login = async (email, password) => {
        const res = await loginService(email, password);
        if (res) {
            console.log(res.data);
            console.log(res.headers);
            setToken(res.headers['authorization']);
            setUser(res.data);
        } else {
            console.error('Login failed');
        }
    };

    const logout = () => {
        clearToken();
        setUser(null);
    };


    return (
        <UserContext.Provider value={{ register, login, logout, user, loading }}>
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = () => useContext(UserContext);