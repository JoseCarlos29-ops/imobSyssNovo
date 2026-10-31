'use client'
import Footer from "../components/Footer";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import { useSelector } from "react-redux";
import { RootState } from "../redux/store";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export default function SistemaLayout({children}){

    const usuario = useSelector((state:RootState)=> state.auth.usuario);

    const router = useRouter();

    useEffect(()=>{
        if(usuario == null){
            router.push("/login");
        }
    })

    return (
        <div className="flex min-h-screen bg-blue-50 text-slate-800">
            <Sidebar />
            <div className="flex flex-col flex-1 min-w-0">
                <Header />
                <main className="flex-1 p-6 md:p-8 overflow-y-auto">
                    {children}
                </main>
                <Footer />
            </div>
        </div>
    );
}