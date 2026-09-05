'use client'

import { handler } from "next/dist/build/templates/app-route";
import { useRouter } from "next/navigation";


export default function Login() {

    
    const router = useRouter();
    const handlerLogin = async(formData: FormData) =>{

        
        router.push("/home")

    }


    return (
      // FUNDO EXTERNO: Adicionados detalhes marcantes em tom marrom quente (amber/stone/marrom) nos cantos e no centro, criando um contraste elegante com o fundo claro.
      <div className="relative min-h-screen w-full flex items-center justify-center bg-stone-100 p-4 antialiased overflow-hidden bg-[radial-gradient(ellipse_at_top_left,rgba(120,53,15,0.25),transparent_50%),radial-gradient(ellipse_at_bottom_right,rgba(69,26,3,0.2),transparent_50%),radial-gradient(circle_at_center,rgba(217,119,6,0.08),transparent_60%),linear-gradient(to_bottom_right,#fafaf9,#f5f5f4)]">
        
        {/* Detalhes/Acentos visuais em marrom flutuando ao fundo */}
        <div className="absolute top-10 left-10 w-72 h-72 bg-amber-900/10 rounded-full blur-3xl pointer-events-none" />
        <div className="absolute bottom-10 right-10 w-96 h-96 bg-amber-950/15 rounded-full blur-3xl pointer-events-none" />
        
        {/* CARD DE LOGIN:*/}
        <div className="relative z-10 w-full max-w-md bg-white border border-stone-200/60 rounded-3xl p-10 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.08),inset_0_0_30px_rgba(180,115,70,0.05)] transition-all duration-300">
          <div>
            {/* TÍTULO */}
            <h1 className="text-xl font-semibold text-black text-center mb-10 tracking-tighter">
              Entrar no sistema
            </h1>
          </div>
          <form action={handlerLogin} className="space-y-5">
            <div>
              {/* LABEL */}
              <label className="block text-xs font-medium text-black mb-1.5 uppercase tracking-wider">
                E-mail
              </label>
              {/* INPUT */}
              <input 
                name="email" 
                className="w-full px-5 py-3.5 bg-stone-50/60 border border-stone-200 rounded-xl text-black placeholder-stone-400 focus:outline-none focus:ring-1.5 focus:ring-black focus:border-black/20 transition-all duration-200 placeholder:font-light"
              />
            </div>
  
            <div>
              <label className="block text-xs font-medium text-black mb-1.5 uppercase tracking-wider">
                senha
              </label>
              <input 
                name="senha" 
                type="password" 
                className="w-full px-5 py-3.5 bg-stone-50/60 border border-stone-200 rounded-xl text-black placeholder-stone-400 focus:outline-none focus:ring-1.5 focus:ring-black focus:border-black/20 transition-all duration-200 placeholder:font-light"
              />
            </div>
  
            {/* BOTÃO */}
            <button 
              type="submit" 
              className="w-full h-14 flex items-center justify-center bg-black hover:bg-stone-900 text-white font-medium rounded-xl shadow-lg shadow-black/10 transition-all duration-300 ease-in-out active:scale-[0.98] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black mt-4"
            >
              Entrar
            </button>
          </form>
        </div>  
      </div>
    );
  }