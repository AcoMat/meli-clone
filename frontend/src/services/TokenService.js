export const setToken = async (token) => {
    try{
        localStorage.setItem('authorization', token);
    }catch(err){
        console.log(err)
    }
}

export const clearToken = async () => {
    try{
        localStorage.removeItem('authorization', '');
        localStorage.removeItem('username', '');
    }catch(err){
        console.log(err)
    }
}

export const getToken = async () => {
    try{
        const token = localStorage.getItem('authorization');
        return token;
    } catch(err){
        console.log(err)
        return null
    }
}