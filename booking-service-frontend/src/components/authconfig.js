import axios from 'axios'

const authApiUrl = 'http://localhost:8080';

export const authConfig = {
    onSignIn: async ({email, password}) => {
        try{
            const response = await axios.post(`${authApiUrl}/login`, {email, password});
            const {token} = response.data;
            return {token};
        } catch (error) {
            throw new Error('login Failed');
        }
    },

    OnSignUp: async ({email, name, password}) => {
        try{
            const response = await axios.post( `${authApiUrl}/signup`, {email, name, password});
            const {token} = response.data;
            return {token};
        } catch(error){
            throw new Error('Signup Failed');
        }
    },

    OnLogout: async () => {   
    },
    accessTokenKey: 'access_token',
    onLogoutRedirectedTo: '/login',
    onLoginRedirectedTo: '/'
};