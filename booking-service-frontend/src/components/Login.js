import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {useAuth} from '@authkit/react';

const Login = ({onClose}) => {
    const {signIn} = useAuth();
    const navigate = useNavigate();
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = async (e) =>{
        e.preventDefault();
        try{
            await signIn({email, password});
            onClose();
            navigate('/');
        } catch (error) {
            console.error('Login Failed', error);
        }
    };

    return (
        <div className='login-modal'>
            <form onSubmit={handleSubmit}>
                <h2>Login</h2>
                <label>Email</label>
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                <label>Password</label>
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;