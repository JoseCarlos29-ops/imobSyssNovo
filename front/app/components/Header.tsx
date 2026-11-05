'use client'
import { useDispatch } from "react-redux";
import { store } from "../redux/store";
import { logout } from "../redux/slices/authSlice";

export default function Header(){

    const usuario = store.getState().auth.usuario;
    const dispatch = useDispatch();

    return (
        <header className="w-full bg-stone-900 border-b border-stone-800 shadow-md">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
                <span className="text-sm font-medium text-stone-300">
                    Usuário: <span className="text-white font-semibold">{usuario ? usuario.nome.toLocaleUpperCase() : 'Usuário não identificado'}</span>
                </span>
                <button
                    onClick={()=>{
                        dispatch(logout());
                    }}
                    className="px-4 py-2 bg-amber-700 hover:bg-amber-600 text-white font-medium text-sm rounded-lg transition-colors duration-200 shadow-sm focus:outline-none"
                >
                    Sair
                </button>
            </div>
        </header>
    );
}