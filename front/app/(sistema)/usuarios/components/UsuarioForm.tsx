'use client'
import axios from "axios";
import { useRouter } from "next/navigation";
import { Usuario, UsuarioFormProps } from "@/app/types/usuario";
import Link from "next/link";
import { useState } from "react";

export default function UsuarioForm({usuarioExistente} : UsuarioFormProps) {

  const router = useRouter()

  const [usuario, setUsuario] = useState<Usuario> (usuarioExistente || new Usuario(null, "", "", "ATIVO", "", "")); 

  


const handlerSalvar = async (formData : FormData) => {

  if(usuarioExistente){

    var dadosRetorno = await axios.put<number>( 'http://localhost:8080/usuarios' +usuario.id, usuario);

  if(dadosRetorno.status ==200){
  
    alert("Usuario foi salvo com sucesso");
    
  }else{

    alert(dadosRetorno.data)
    return;
  }

  router.push("/usuarios")



  }else{

  var dadosRetorno = await axios.post<number>( 'http://localhost:8080/usuarios', usuario);

  if(dadosRetorno.status ==200){
  
    alert("Usuario foi salvo com sucesso");
    
  }else{

    alert(dadosRetorno.data)
    return;
  }

  router.push("/usuarios")

}
}

  const handlerChange = ( campos: 'nome' | 'email' | 'cpf' | 'senha', valor:string) => {
    setUsuario(valorAnterior => new Usuario(

      valorAnterior.id,
      campos === 'nome' ? valor : valorAnterior.nome,
      campos === 'email' ? valor : valorAnterior.email,
      valorAnterior.status,
      campos === 'cpf' ? valor : valorAnterior.cpf,
      campos === 'senha' ? valor: valorAnterior.senha
      

    ) )
  }

  return (

  

    <div className="max-w-2xl mx-auto">
      {/* Cabeçalho do Formulário */}
      <div className="mb-6">
        <h1 className="text-2xl md:text-3xl font-bold text-black tracking-tight">
          Novo Usuário
        </h1>
        <p className="text-xs text-stone-500 uppercase tracking-wider mt-1">
          Cadastre um novo membro no sistema
        </p>
      </div>

      {/* Card do Formulário */}
      <form action = {handlerSalvar} className="bg-white border border-stone-200/60 rounded-3xl p-6 md:p-8 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05)]">
        <div className="space-y-5">
          
          {/* Nome Completo */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              Nome completo
            </label>
            <input
              type="text"
              name="nome"
              required
              onChange={(e)=> handlerChange('nome', e.target.value)}
              value = {usuario.nome}
              placeholder="Nome completo"

              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            />
          </div>

          {/* Grid CPF e E-mail */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                CPF
              </label>
              <input
                type="text"
                name="cpf"
                required
                onChange={(e)=> handlerChange('cpf', e.target.value)}
                value = {usuario.cpf}
                
                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                E-mail
              </label>
              <input
                type="email"
                name="email"
                required
                onChange={(e)=> handlerChange('email', e.target.value)}
                value = {usuario.email}
                placeholder="Email@outlook.com.br"
                
                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
              />
            </div>
          </div>

          {/* Senha */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              Senha
            </label>
            <input
              type="password"
              name="senha"
              required
              onChange={(e)=> handlerChange('senha', e.target.value)}
              value = {usuario.senha}
              placeholder="*********"
             
              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            />
          </div>

          {/* Botões de Ação */}
          <div className="pt-4 flex items-center justify-end gap-3 border-t border-stone-100">
            <Link
              href="/usuarios"
              className="px-6 py-3 bg-stone-100 hover:bg-stone-200 text-stone-700 text-xs font-semibold uppercase tracking-wider rounded-xl border border-stone-200 transition-all duration-200"
            >
              Cancelar
            </Link>

            <button
              type="submit"
              className="px-6 py-3 bg-black hover:bg-stone-900 text-white text-xs font-semibold uppercase tracking-wider rounded-xl shadow-lg shadow-black/10 transition-all duration-200 active:scale-[0.98] focus:outline-none focus:ring-2 focus:ring-black"
            >
              Salvar
            </button>
          </div>

        </div>
      </form>
    </div>
  );
}