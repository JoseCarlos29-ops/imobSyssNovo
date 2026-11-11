'use client'

import axios from "@/node_modules/axios/index"
import Cookies from "js-cookie";
import { store } from "../redux/store";
import { logout } from "../redux/slices/authSlice";

const api = axios.create({
    baseURL:'http://localhost:8080'
});

api.interceptors.request.use(
    (config) =>{
        const token = Cookies.get('token');

        if(token){
            config.headers.Authorization =`Bearer ${token}`;
        }

        return config;
    }
);

// Interceptor de resposta: 401 (token expirado/invalido) -> logout no Redux
// e o layout do sistema volta para a tela de login
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response && error.response.status === 401) {
            store.dispatch(logout());
        }
        return Promise.reject(error);
    }
);

export default api;