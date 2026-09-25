'use client'
import axios from "axios";
import { useRouter } from "next/navigation";
import { Cliente, ClienteFormProps } from "@/app/types/clientes";
import Link from "next/link";
import { useState } from "react";

export default function ClienteForm({ clienteExistente }: ClienteFormProps) {

  const router = useRouter()

  const [cliente, setCliente] = useState<Cliente>(
    clienteExistente || new Cliente(
        null,
        "",
        "",
        "",
        "",
        "",
        "ATIVO"
    )
);



  const handlerSalvar = async (formData: FormData) => {

    if (clienteExistente) {

      var dadosRetorno = await axios.put<number>('http://localhost:8080/clientes/' + cliente.id, cliente);

      if (dadosRetorno.status == 200) {

        alert("Cliente foi salvo com sucesso");

      } else {

        alert(dadosRetorno.data)
        return;
      }

      router.push("/clientes")

    } else {

      var dadosRetorno = await axios.post<number>('http://localhost:8080/clientes', cliente);

      if (dadosRetorno.status == 200) {

        alert("Cliente foi salvo com sucesso");

      } else {

        alert(dadosRetorno.data)
        return;
      }

      router.push("/clientes")

    }
  }

  const handlerChange = (campos: 'nome' | 'cpf' | 'email' | 'telefone' | 'endereco' | 'statusCliente', valor: string) => {
    setCliente(valorAnterior => new Cliente(

      valorAnterior.id,
      campos === 'nome' ? valor : valorAnterior.nome,
      campos === 'cpf' ? valor : valorAnterior.cpf,
      campos === 'email' ? valor : valorAnterior.email,
      campos === 'telefone' ? valor : valorAnterior.telefone,
      campos === 'endereco' ? valor : valorAnterior.endereco,
      campos === 'statusCliente' ? valor : valorAnterior.statusCliente

    ))
  }

  return (

    <div className="max-w-2xl mx-auto">
      {/* Cabeçalho do Formulário */}
      <div className="mb-6">
        <h1 className="text-2xl md:text-3xl font-bold text-black tracking-tight">
          {clienteExistente ? "Editar Cliente" : "Novo Cliente"}
        </h1>
        <p className="text-xs text-stone-500 uppercase tracking-wider mt-1">
          {clienteExistente ? "Atualize os dados do cliente no sistema" : "Cadastre um novo cliente no sistema"}
        </p>
      </div>

      {/* Card do Formulário */}
      <form action={handlerSalvar} className="bg-white border border-stone-200/60 rounded-3xl p-6 md:p-8 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05)]">
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
              onChange={(e) => handlerChange('nome', e.target.value)}
              value={cliente.nome}
              placeholder="Digite o nome completo"

              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            />
          </div>

          {/* Grid CPF e Telefone */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                CPF
              </label>
              <input
                type="text"
                name="cpf"
                required
                onChange={(e) => handlerChange('cpf', e.target.value)}
                value={cliente.cpf}
                placeholder="000.000.000-00"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Telefone
              </label>
              <input
                type="tel"
                name="telefone"
                required
                onChange={(e) => handlerChange('telefone', e.target.value)}
                value={cliente.telefone}
                placeholder="(00) 00000-0000"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>
          </div>

          {/* E-mail */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              E-mail
            </label>
            <input
              type="email"
              name="email"
              required
              onChange={(e) => handlerChange('email', e.target.value)}
              value={cliente.email}
              placeholder="exemplo@email.com"

              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            />
          </div>

          {/* Endereço */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              Endereço
            </label>
            <input
              type="text"
              name="endereco"
              required
              onChange={(e) => handlerChange('endereco', e.target.value)}
              value={cliente.endereco}
              placeholder="Rua, número, bairro, cidade"

              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            />
          </div>

          {/* Status */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              Status
            </label>
            <select
              name="statusCliente"
              required
              onChange={(e) => handlerChange('statusCliente', e.target.value)}
              value={cliente.statusCliente}
              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            >
              <option value="" disabled>Selecione o status</option>
              <option value="ATIVO">Ativo</option>
              <option value="INATIVO">Inativo</option>
            </select>
          </div>

          {/* Botões de Ação */}
          <div className="pt-4 flex items-center justify-end gap-3 border-t border-stone-100">
            <Link
              href="/clientes"
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