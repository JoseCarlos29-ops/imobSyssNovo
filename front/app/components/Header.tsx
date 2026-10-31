'use client'
import { useDispatch } from "react-redux";
import { store } from "../redux/store";
import { logout } from "../redux/slices/authSlice";

export default function Header(){

    const usuario = store.getState().auth.usuario;
    const dispatch = useDispatch();

    return (
        <header className="w-full bg-blue-900 border-b border-blue-800 shadow-md">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
                <span>
                    Usuário: {usuario ? usuario.nome.toLocaleUpperCase() : 'Usuário não identificado'}
                </span>
                <button
                    onClick={()=>{
                        dispatch(logout());
                    }}
                    className="px-4 py-2 bg-blue-600 hover:bg-blue-500 text-white font-medium text-sm rounded-lg transition-colors duration-200 shadow-sm focus:outline-none"
                >
                    Sair
                </button>
            </div>
        </header>
    );
}