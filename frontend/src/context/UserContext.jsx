import { createContext, useContext, useEffect, useState } from 'react';
import { clearToken, getToken, setToken } from '../services/TokenService';
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

    /*
    * Register a new user
    * @param {string} firstName - The first name of the user
    * @param {string} lastName - The last name of the user
    * @param {string} email - The email of the user
    * @param {string} password - The password of the user
    * @throws {Error} If the registration fails
    */
    const register = async (firstName, lastName, email, password) => {
        const res = await registerService(firstName, lastName, email, password);
        setToken(res.headers['authorization']);
        setUser(res.data);
    }

    const login = async (email, password) => {
        const res = await loginService(email, password);
        setToken(res.headers['authorization']);
        setUser(res.data);
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