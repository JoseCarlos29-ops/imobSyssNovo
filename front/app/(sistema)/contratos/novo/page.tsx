'use client'

import { useRouter } from "next/navigation";
import Cookies from "js-cookie";
import { useDispatch } from "react-redux";
import { login } from "@/app/redux/slices/authSlice";
import { buscarUsuarioLogado } from "@/app/services/usuarioService";
import { loginBack } from "@/app/services/authService";
export default function Login(){
  const router = useRouter();
  const dispatch = useDispatch();

  const handleLogin = async(formData: FormData) => {
      try {
          const emailTela = formData.get("email")?.toString() ?? "";
          const senhaTela = formData.get("senha")?.toString() ?? "";

          var loginResposta = await loginBack(emailTela, senhaTela);

          if(loginResposta != null){
              // o token precisa estar no cookie antes de buscar o usuario logado
              Cookies.set('token', loginResposta.token, { secure: true, expires: 7 });

              var usuarioLogado = await buscarUsuarioLogado();

              dispatch(login(
                  {
                      token: loginResposta.token,
                      usuario: {...usuarioLogado}
                  }
              ));
              router.push("/home")
          }else{
              alert("Login ou senha Invalido!")
          }
      } catch (error) {
          alert("Login ou senha Invalido!")
      }
  }

  return (
    // FUNDO EXTERNO: Mesmo padrão com gradientes radiais idênticos ao da página de contratos
    <div className="relative min-h-screen w-full flex items-center justify-center bg-stone-100 p-4 antialiased overflow-hidden bg-[radial-gradient(ellipse_at_top_left,rgba(120,53,15,0.15),transparent_50%),radial-gradient(ellipse_at_bottom_right,rgba(69,26,3,0.1),transparent_50%),linear-gradient(to_bottom_right,#fafaf9,#f5f5f4)]">
      
      {/* Detalhes de fundo flutuantes em tons quentes */}
      <div className="absolute top-10 left-10 w-96 h-96 bg-amber-900/5 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute bottom-10 right-10 w-96 h-96 bg-amber-950/10 rounded-full blur-3xl pointer-events-none" />
      
      {/* CARD DE LOGIN: Ajustado com o mesmo arredondamento, bordas e sombras da listagem de contratos */}
      <div className="relative z-10 w-full max-w-md bg-white border border-stone-200/60 rounded-3xl p-8 sm:p-10 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05),inset_0_0_30px_rgba(180,115,70,0.03)] transition-all duration-300">
        <div>
          {/* TÍTULO */}
          <h1 className="text-xl sm:text-2xl font-semibold text-black text-center mb-8 tracking-tighter">
            Entrar no sistema
          </h1>
        </div>
        <form action={handleLogin} className="space-y-5">
          <div>
            {/* LABEL */}
            <label className="block text-xs font-semibold text-stone-700 mb-1.5 uppercase tracking-wider">
              E-mail
            </label>
            {/* INPUT */}
            <input 
              name="email" 
              className="w-full px-4 py-3 bg-stone-50/80 border border-stone-200 rounded-xl text-black placeholder-stone-400 focus:outline-none focus:ring-1.5 focus:ring-black focus:border-black/20 transition-all duration-200 text-sm"
            />
          </div>

          <div>
            <label className="block text-xs font-semibold text-stone-700 mb-1.5 uppercase tracking-wider">
              Senha
            </label>
            <input 
              name="senha" 
              type="password" 
              className="w-full px-4 py-3 bg-stone-50/80 border border-stone-200 rounded-xl text-black placeholder-stone-400 focus:outline-none focus:ring-1.5 focus:ring-black focus:border-black/20 transition-all duration-200 text-sm"
            />
          </div>

          {/* BOTÃO */}
          <button 
            type="submit" 
            className="w-full h-12 flex items-center justify-center bg-black hover:bg-stone-900 text-white font-medium text-sm rounded-xl shadow-lg shadow-black/10 transition-all duration-300 ease-in-out active:scale-[0.98] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-black mt-4"
          >
            Entrar
          </button>
        </form>
      </div>  
    </div>
  );
}