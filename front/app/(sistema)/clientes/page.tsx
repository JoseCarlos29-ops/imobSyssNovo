"use client"
import { Cliente } from "@/app/types/clientes";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Clientes() {
  const [clientes, setClientes] = useState<Cliente[]>([]);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const dados = await axios.get<Cliente[]>("http://localhost:8080/clientes");
      setClientes(dados.data);
    } catch (error) {
      alert("Erro ao carregar dados!");
    }
  };

  return (
    <div className="relative min-h-screen w-full bg-stone-100 p-6 md:p-10 font-sans antialiased overflow-hidden bg-[radial-gradient(ellipse_at_top_left,rgba(120,53,15,0.15),transparent_50%),radial-gradient(ellipse_at_bottom_right,rgba(69,26,3,0.1),transparent_50%),linear-gradient(to_bottom_right,#fafaf9,#f5f5f4)]">
      
      {/* Detalhes de fundo em tons quentes */}
      <div className="absolute top-10 left-10 w-96 h-96 bg-amber-900/5 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute bottom-10 right-10 w-96 h-96 bg-amber-950/10 rounded-full blur-3xl pointer-events-none" />

      <div className="relative z-10 max-w-7xl mx-auto space-y-8">
        {/* Cabeçalho */}
        <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
          <div>
            <h1 className="text-2xl md:text-3xl font-semibold text-black tracking-tighter">
              Gestão de clientes
            </h1>
            <p className="text-xs text-stone-500 uppercase tracking-wider mt-1">
              Painel de Controle
            </p>
          </div>

          <Link
            href="/clientes/novo"
            className="inline-flex items-center justify-center px-6 py-3 bg-black hover:bg-stone-900 text-white font-medium text-sm rounded-xl shadow-lg shadow-black/10 transition-all duration-300 active:scale-[0.98] focus:outline-none focus:ring-2 focus:ring-black focus:ring-offset-2"
          >
            + Novo Cliente
          </Link>
        </div>

        {/* Tabela de Clientes */}
        <div className="bg-white border border-stone-200/60 rounded-3xl p-2 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05),inset_0_0_30px_rgba(180,115,70,0.03)] overflow-hidden">
          <div className="overflow-x-auto w-full">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-stone-100">
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Código
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Nome
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    CPF
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    E-mail
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Telefone
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Endereço
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Status
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-stone-100">
                {clientes.map((cliente) => (
                  <tr
                    key={cliente.id}
                    className="hover:bg-stone-50/80 transition-colors duration-200 text-stone-800"
                  >
                    <td className="px-6 py-4 text-sm font-medium text-black">
                      #{cliente.id}
                    </td>
                    <td className="px-6 py-4 text-sm font-medium">
                      {cliente.nome}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600 font-mono">
                      {cliente.cpf}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {cliente.email}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {cliente.telefone}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {cliente.endereco}
                    </td>
                    <td className="px-6 py-4 text-sm">
                      <span className="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium bg-amber-900/10 text-amber-900 border border-amber-900/20">
                        {cliente.statusCliente}
                      </span>
                    </td>
                  </tr>
                ))}

                {clientes.length === 0 && (
                  <tr>
                    <td
                      colSpan={7}
                      className="px-6 py-16 text-center text-stone-400 text-sm font-light italic"
                    >
                      Nenhum cliente encontrado!
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}