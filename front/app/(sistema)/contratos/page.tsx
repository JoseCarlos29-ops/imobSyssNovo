"use client"
import { Contrato } from "@/app/types/contrato";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Contratos() {
  const [contratos, setContratos] = useState<Contrato[]>([]);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const dados = await axios.get<Contrato[]>("http://localhost:8080/contratos");
      setContratos(dados.data);
    } catch (error) {
      alert("Erro ao carregar dados!");
    }
  };

  const handleDeletarContrato = async (contrato: Contrato) => {

    var dadosRetorno = await axios.delete('http://localhost:8080/contratos/' + contrato.id + '/excluirContrato');

    if (dadosRetorno.status == 200) {

      alert("Contrato foi deletado com sucesso");

    } else {

      alert(dadosRetorno.data)
      return;
    }

    carregarDados();

  }

  const proximoStatus = (statusAtual: string) => {
    if (statusAtual === "ATIVO") return "ENCERRADO";
    if (statusAtual === "ENCERRADO") return "CANCELADO";
    return "ATIVO";
  }

  const handleAlterarStatusContrato = async (contrato: Contrato) => {

    const novoStatus = { statusContrato: proximoStatus(contrato.statusContrato) };

    try {
      var dadosRetorno = await axios.patch('http://localhost:8080/contratos/' + contrato.id + '/statusContrato', novoStatus);

      if (dadosRetorno.status == 200) {
        alert("Status atualizado com sucesso!");
      } else {
        alert(dadosRetorno.data);
        return;
      }

      carregarDados();
    } catch (error) {
      alert("Erro ao atualizar status!");
    }

  }

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
              Gestão de contratos
            </h1>
            <p className="text-xs text-stone-500 uppercase tracking-wider mt-1">
              Painel de Controle
            </p>
          </div>

          <Link
            href="/contratos/novo"
            className="inline-flex items-center justify-center px-6 py-3 bg-black hover:bg-stone-900 text-white font-medium text-sm rounded-xl shadow-lg shadow-black/10 transition-all duration-300 active:scale-[0.98] focus:outline-none focus:ring-2 focus:ring-black focus:ring-offset-2"
          >
            + Novo Contrato
          </Link>
        </div>

        {/* Tabela de Contratos */}
        <div className="bg-white border border-stone-200/60 rounded-3xl p-2 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05),inset_0_0_30px_rgba(180,115,70,0.03)] overflow-hidden">
          <div className="overflow-x-auto w-full">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-stone-100">
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Código
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Tipo
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Valor do contrato
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Valor da comissão
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Data
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Status
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider text-right">
                    Ações
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-stone-100">
                {contratos.map((contrato) => (
                  <tr
                    key={contrato.id}
                    className="hover:bg-stone-50/80 transition-colors duration-200 text-stone-800"
                  >
                    <td className="px-6 py-4 text-sm font-medium text-black">
                      #{contrato.id}
                    </td>
                    <td className="px-6 py-4 text-sm font-medium">
                      {contrato.tipo}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {contrato.valorContrato}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {contrato.valorComissao}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {contrato.dataContrato}
                    </td>
                    <td className="px-6 py-4 text-sm">
                      <span className="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium bg-amber-900/10 text-amber-900 border border-amber-900/20">
                        {contrato.statusContrato}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-sm text-right">
                      <div className="flex items-center justify-end gap-2">
                        <Link
                          href={`/contratos/${contrato.id}/editar`}
                          className="px-4 py-2 bg-stone-100 hover:bg-stone-200 text-stone-700 text-xs font-semibold uppercase tracking-wider rounded-lg border border-stone-200 transition-all duration-200"
                        >
                          Editar
                        </Link>

                        <button
                          onClick={() => handleAlterarStatusContrato(contrato)}
                          className={`px-4 py-2 text-xs font-semibold uppercase tracking-wider rounded-lg border transition-all duration-200 ${
                            contrato.statusContrato === 'ATIVO'
                              ? 'bg-amber-50 hover:bg-amber-100 text-amber-700 border-amber-100'
                              : contrato.statusContrato === 'ENCERRADO'
                              ? 'bg-red-50 hover:bg-red-100 text-red-700 border-red-100'
                              : 'bg-green-50 hover:bg-green-100 text-green-700 border-green-100'
                          }`}
                        >
                          {contrato.statusContrato === 'ATIVO' ? 'Encerrar' : contrato.statusContrato === 'ENCERRADO' ? 'Cancelar' : 'Reativar'}
                        </button>

                        <button
                          onClick={() => handleDeletarContrato(contrato)}
                          className="px-4 py-2 bg-red-50 hover:bg-red-100 text-red-700 text-xs font-semibold uppercase tracking-wider rounded-lg border border-red-100 transition-all duration-200"
                        >
                          Excluir
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}

                {contratos.length === 0 && (
                  <tr>
                    <td
                      colSpan={7}
                      className="px-6 py-16 text-center text-stone-400 text-sm font-light italic"
                    >
                      Nenhum contrato encontrado!
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