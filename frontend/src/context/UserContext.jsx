import { createContext, useContext, useEffect, useState, useMemo, useCallback } from 'react';
import { clearToken, getToken, setToken } from '../services/TokenService';
import { getUserProfile, hasAdminAccess, login as loginService, register as registerService } from '../services/ApiService';

export const UserContext = createContext()

export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    const loadUser = async () => {
        setLoading(true);
        const token = await getToken();
        if (token && token !== '') {
            const userData = await getUserProfile(token);
            if (userData) {
                const isAdmin = await hasAdminAccess(token);
                setUser({ ...userData, isAdmin });
            } else {
                clearToken();
                setUser(null);
            }
        } else {
            clearToken();
            setUser(null);

        }
        setLoading(false);
    };

    useEffect(() => {
        loadUser();
    }, []);

    const register = useCallback(async (firstName, lastName, email, password) => {
        const res = await registerService(firstName, lastName, email, password);
        setToken(res.headers['authorization']);
        const isAdmin = await hasAdminAccess(res.headers['authorization']);
        setUser({ ...res.data, isAdmin });
    }, []);

    const login = useCallback(async (email, password) => {
        const res = await loginService(email, password);
        setToken(res.headers['authorization']);
        const isAdmin = await hasAdminAccess(res.headers['authorization']);
        setUser({ ...res.data, isAdmin });
    }, []);

    const logout = useCallback(() => {
        clearToken();
        setUser(null);
    }, []);

    // Memoize the context value to prevent unnecessary re-renders
    const contextValue = useMemo(() => ({
        register,
        login,
        logout,
        user,
        loading
    }), [register, login, logout, user, loading]);

    return (
        <UserContext.Provider value={contextValue}>
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = () => useContext(UserContext);