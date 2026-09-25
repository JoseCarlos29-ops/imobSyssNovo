'use client'
import axios from "axios";
import { useRouter } from "next/navigation";
import { Contrato, ContratoFormProps } from "@/app/types/contrato";
import Link from "next/link";
import { useState } from "react";

const timestampParaInputDate = (timestamp: number) => {
  if (!timestamp) return "";
  const data = new Date(timestamp);
  return data.toISOString().split('T')[0];
}

export default function ContratoForm({ contratoExistente }: ContratoFormProps) {

  const router = useRouter()

  const [contrato, setContrato] = useState<Contrato>(contratoExistente || new Contrato(null, "", 0, 0, 0, "ATIVO"));



  const handlerSalvar = async (formData: FormData) => {

    if (contratoExistente) {

      var dadosRetorno = await axios.put<number>('http://localhost:8080/contratos/' + contrato.id + '/atualizarContratos', contrato);

      if (dadosRetorno.status == 200) {

        alert("Contrato foi salvo com sucesso");

      } else {

        alert(dadosRetorno.data)
        return;
      }

      router.push("/contratos")

    } else {

      var dadosRetorno = await axios.post<number>('http://localhost:8080/contratos', contrato);

      if (dadosRetorno.status == 200) {

        alert("Contrato foi salvo com sucesso");

      } else {

        alert(dadosRetorno.data)
        return;
      }

      router.push("/contratos")

    }
  }

  const handlerChange = (campos: 'tipo' | 'valorContrato' | 'valorComissao' | 'dataContrato' | 'statusContrato', valor: string) => {
    setContrato(valorAnterior => new Contrato(

      valorAnterior.id,
      campos === 'tipo' ? valor : valorAnterior.tipo,
      campos === 'valorContrato' ? Number(valor) : valorAnterior.valorContrato,
      campos === 'valorComissao' ? Number(valor) : valorAnterior.valorComissao,
      campos === 'dataContrato' ? new Date(valor).getTime() : valorAnterior.dataContrato,
      campos === 'statusContrato' ? valor : valorAnterior.statusContrato

    ))
  }

  return (

    <div className="max-w-2xl mx-auto">
      {/* Cabeçalho do Formulário */}
      <div className="mb-6">
        <h1 className="text-2xl md:text-3xl font-bold text-black tracking-tight">
          {contratoExistente ? "Editar Contrato" : "Novo Contrato"}
        </h1>
        <p className="text-xs text-stone-500 uppercase tracking-wider mt-1">
          {contratoExistente ? "Atualize os dados do contrato no sistema" : "Cadastre um novo contrato no sistema"}
        </p>
      </div>

      {/* Card do Formulário */}
      <form action={handlerSalvar} className="bg-white border border-stone-200/60 rounded-3xl p-6 md:p-8 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05)]">
        <div className="space-y-5">

          {/* Tipo */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              Tipo
            </label>
            <select
              name="tipo"
              required
              onChange={(e) => handlerChange('tipo', e.target.value)}
              value={contrato.tipo}
              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            >
              <option value="" disabled>Selecione o tipo</option>
              <option value="Locação">Locação</option>
              <option value="Venda">Venda</option>
            </select>
          </div>

          {/* Grid Valor do Contrato e Valor da Comissão */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Valor do contrato
              </label>
              <input
                type="number"
                name="valorContrato"
                step="0.01"
                required
                onChange={(e) => handlerChange('valorContrato', e.target.value)}
                value={contrato.valorContrato}
                placeholder="0,00"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Valor da comissão
              </label>
              <input
                type="number"
                name="valorComissao"
                step="0.01"
                required
                onChange={(e) => handlerChange('valorComissao', e.target.value)}
                value={contrato.valorComissao}
                placeholder="0,00"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>
          </div>

          {/* Grid Data e Status */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Data do contrato
              </label>
              <input
                type="date"
                name="dataContrato"
                required
                onChange={(e) => handlerChange('dataContrato', e.target.value)}
                value={timestampParaInputDate(contrato.dataContrato)}
                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Status
              </label>
              <select
                name="statusContrato"
                required
                onChange={(e) => handlerChange('statusContrato', e.target.value)}
                value={contrato.statusContrato}
                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
              >
                <option value="" disabled>Selecione o status</option>
                <option value="EM_ELABORACAO">Em elaboração</option>
                <option value="AGUARDANDO_ASSINATURA">Aguardando assinatura</option>
                <option value="ATIVO">Ativo</option>
                <option value="SUSPENSO">Suspenso</option>
                <option value="INADIMPLENTE">Inadimplente</option>
                <option value="RENOVADO">Renovado</option>
                <option value="ENCERRADO">Encerrado</option>
                <option value="RESCINDIDO">Rescindido</option>
                <option value="CANCELADO">Cancelado</option>
              </select>
            </div>
          </div>

          {/* Botões de Ação */}
          <div className="pt-4 flex items-center justify-end gap-3 border-t border-stone-100">
            <Link
              href="/contratos"
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