"use client"
import { Imovel } from "@/app/types/imovel";
import axios from "axios";
import Link from "next/link";
import { useEffect, useState } from "react";

export default function Imoveis() {
  const [imoveis, setImoveis] = useState<Imovel[]>([]);

  useEffect(() => {
    carregarDados();
  }, []);

  const carregarDados = async () => {
    try {
      const dados = await axios.get<Imovel[]>("http://localhost:8080/imoveis");
      setImoveis(dados.data);
    } catch (error) {
      alert("Erro ao carregar dados!");
    }
  };

  const handleDeletarImovel = async (imovel: Imovel) => {

    var dadosRetorno = await axios.delete('http://localhost:8080/imoveis/' + imovel.id + '/excluirImovel');

    if (dadosRetorno.status == 200) {

      alert("Imóvel foi deletado com sucesso");

    } else {

      alert(dadosRetorno.data)
      return;
    }

    carregarDados();

  }

  const proximoStatus = (statusAtual: string) => {
    if (statusAtual === "DISPONIVEL") return "ALUGADO";
    if (statusAtual === "ALUGADO") return "VENDIDO";
    return "DISPONIVEL";
  }

  const handleAlterarStatusImovel = async (imovel: Imovel) => {

    var novoStatus = { statusImovel: proximoStatus(imovel.status) };

    var dadosRetorno = await
      axios.patch('http://localhost:8080/imoveis/' + imovel.id + '/statusImovel', novoStatus);

    if (dadosRetorno.status == 200) {
      alert("Status atualizado com sucesso!");
    } else {
      alert(dadosRetorno.data);

      return;
    }

    carregarDados();

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
              Gestão de imóveis
            </h1>
            <p className="text-xs text-stone-500 uppercase tracking-wider mt-1">
              Painel de Controle
            </p>
          </div>

          <Link
            href="/imovel/novo"
            className="inline-flex items-center justify-center px-6 py-3 bg-black hover:bg-stone-900 text-white font-medium text-sm rounded-xl shadow-lg shadow-black/10 transition-all duration-300 active:scale-[0.98] focus:outline-none focus:ring-2 focus:ring-black focus:ring-offset-2"
          >
            + Novo Imóvel
          </Link>
        </div>

        {/* Tabela de Imóveis */}
        <div className="bg-white border border-stone-200/60 rounded-3xl p-2 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05),inset_0_0_30px_rgba(180,115,70,0.03)] overflow-hidden">
          <div className="overflow-x-auto w-full">
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-stone-100">
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Código
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Endereço
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Tipo
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Valor
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Área
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Quartos
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Banheiros
                  </th>
                  <th className="px-6 py-4 text-xs font-semibold text-black uppercase tracking-wider">
                    Proprietário
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
                {imoveis.map((imovel) => (
                  <tr
                    key={imovel.id}
                    className="hover:bg-stone-50/80 transition-colors duration-200 text-stone-800"
                  >
                    <td className="px-6 py-4 text-sm font-medium text-black">
                      #{imovel.id}
                    </td>
                    <td className="px-6 py-4 text-sm font-medium">
                      {imovel.endereco}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {imovel.tipo}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {imovel.valor}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {imovel.area}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {imovel.quartos}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {imovel.banheiros}
                    </td>
                    <td className="px-6 py-4 text-sm text-stone-600">
                      {imovel.proprietario}
                    </td>
                    <td className="px-6 py-4 text-sm">
                      <span className="inline-flex items-center px-2.5 py-1 rounded-full text-xs font-medium bg-amber-900/10 text-amber-900 border border-amber-900/20">
                        {imovel.status}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-sm text-right">
                      <div className="flex items-center justify-end gap-2">
                        <Link
                          href={`/imovel/${imovel.id}/editar`}
                          className="px-4 py-2 bg-stone-100 hover:bg-stone-200 text-stone-700 text-xs font-semibold uppercase tracking-wider rounded-lg border border-stone-200 transition-all duration-200"
                        >
                          Editar
                        </Link>

                        <button
                          onClick={() => handleAlterarStatusImovel(imovel)}
                          className={`px-4 py-2 text-xs font-semibold uppercase tracking-wider rounded-lg border transition-all duration-200 ${
                            imovel.status === 'DISPONIVEL'
                              ? 'bg-blue-50 hover:bg-blue-100 text-blue-700 border-blue-100'
                              : imovel.status === 'ALUGADO'
                              ? 'bg-amber-50 hover:bg-amber-100 text-amber-700 border-amber-100'
                              : 'bg-green-50 hover:bg-green-100 text-green-700 border-green-100'
                          }`}
                        >
                          {imovel.status === 'DISPONIVEL' ? 'Alugar' : imovel.status === 'ALUGADO' ? 'Vender' : 'Reabrir'}
                        </button>

                        <button
                          onClick={() => handleDeletarImovel(imovel)}
                          className="px-4 py-2 bg-red-50 hover:bg-red-100 text-red-700 text-xs font-semibold uppercase tracking-wider rounded-lg border border-red-100 transition-all duration-200"
                        >
                          Excluir
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}

                {imoveis.length === 0 && (
                  <tr>
                    <td
                      colSpan={10}
                      className="px-6 py-16 text-center text-stone-400 text-sm font-light italic"
                    >
                      Nenhum imóvel encontrado!
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