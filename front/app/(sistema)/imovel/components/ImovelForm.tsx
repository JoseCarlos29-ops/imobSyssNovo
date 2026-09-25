'use client'
import axios from "axios";
import { useRouter } from "next/navigation";
import { Imovel, ImovelFormProps } from "@/app/types/imovel";
import Link from "next/link";
import { useState } from "react";

export default function ImovelForm({ imovelExistente }: ImovelFormProps) {

  const router = useRouter()

  const [imovel, setImovel] = useState<Imovel>(imovelExistente || new Imovel(null, "", "", 0, 0, 0, 0, "", "DISPONIVEL", ""));



  const handlerSalvar = async (formData: FormData) => {

    if (imovelExistente) {

      var dadosRetorno = await axios.put<number>('http://localhost:8080/imoveis/' + imovel.id + '/atualizarImovel', imovel);

      if (dadosRetorno.status == 200) {

        alert("Imóvel foi salvo com sucesso");

      } else {

        alert(dadosRetorno.data)
        return;
      }

      router.push("/imovel")

    } else {

      var dadosRetorno = await axios.post<number>('http://localhost:8080/imoveis', imovel);

      if (dadosRetorno.status == 200) {

        alert("Imóvel foi salvo com sucesso");

      } else {

        alert(dadosRetorno.data)
        return;
      }

      router.push("/imovel")

    }
  }


      const handlerChange = (campos: 'endereco' | 'tipo' | 'valor' | 'area' | 'quartos' | 'banheiros' | 'descricao' | 'status' | 'proprietario', valor: string) => {
        setImovel(valorAnterior => new Imovel(
      
          valorAnterior.id,
          campos === 'endereco' ? valor : valorAnterior.endereco,
          campos === 'tipo' ? valor : valorAnterior.tipo,
          campos === 'valor' ? Number(valor) : valorAnterior.valor,
          campos === 'area' ? Number(valor) : valorAnterior.area,
          campos === 'quartos' ? Number(valor) : valorAnterior.quartos,
          campos === 'banheiros' ? Number(valor) : valorAnterior.banheiros,
          campos === 'descricao' ? valor : valorAnterior.descricao,
          campos === 'status' ? valor : valorAnterior.status,
          campos === 'proprietario' ? valor : valorAnterior.proprietario
      
        ))
      }

  return (

    <div className="max-w-2xl mx-auto">
      {/* Cabeçalho do Formulário */}
      <div className="mb-6">
        <h1 className="text-2xl md:text-3xl font-bold text-black tracking-tight">
          {imovelExistente ? "Editar Imóvel" : "Novo Imóvel"}
        </h1>
        <p className="text-xs text-stone-500 uppercase tracking-wider mt-1">
          {imovelExistente ? "Atualize os dados do imóvel no sistema" : "Cadastre um novo imóvel no sistema"}
        </p>
      </div>

      {/* Card do Formulário */}
      <form action={handlerSalvar} className="bg-white border border-stone-200/60 rounded-3xl p-6 md:p-8 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05)]">
        <div className="space-y-5">

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
              value={imovel.endereco}
              placeholder="Rua, número, bairro, cidade"

              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            />
          </div>

          {/* Grid Tipo e Valor */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Tipo
              </label>
              <select
                name="tipo"
                required
                onChange={(e) => handlerChange('tipo', e.target.value)}
                value={imovel.tipo}
                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
              >
                <option value="" disabled>Selecione o tipo</option>
                <option value="CASA">Casa</option>
                <option value="APARTAMENTO">Apartamento</option>
                <option value="TERRENO">Terreno</option>
                <option value="COMERCIAL">Comercial</option>
              </select>
            </div>

            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Valor
              </label>
              <input
                type="number"
                name="valor"
                step="0.01"
                required
                onChange={(e) => handlerChange('valor', e.target.value)}
                value={imovel.valor}
                placeholder="0,00"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>
          </div>

          {/* Grid Área e Quartos */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Área (m²)
              </label>
              <input
                type="number"
                name="area"
                required
                onChange={(e) => handlerChange('area', e.target.value)}
                value={imovel.area}
                placeholder="Ex: 80"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Quartos
              </label>
              <input
                type="number"
                name="quartos"
                required
                onChange={(e) => handlerChange('quartos', e.target.value)}
                value={imovel.quartos}
                placeholder="Ex: 2"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>
          </div>

          {/* Grid Banheiros e Proprietário */}
          <div className="grid grid-cols-1 md:grid-cols-2 gap-5">
            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Banheiros
              </label>
              <input
                type="number"
                name="banheiros"
                required
                onChange={(e) => handlerChange('banheiros', e.target.value)}
                value={imovel.banheiros}
                placeholder="Ex: 1"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>

            <div>
              <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
                Proprietário
              </label>
              <input
                type="text"
                name="proprietario"
                required
                onChange={(e) => handlerChange('proprietario', e.target.value)}
                value={imovel.proprietario}
                placeholder="Nome do proprietário"

                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
              />
            </div>
          </div>

          {/* Status */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              Status
            </label>
            <select
              name="status"
              required
              onChange={(e) => handlerChange('status', e.target.value)}
              value={imovel.status}
              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            >
              <option value="" disabled>Selecione o status</option>
              <option value="DISPONIVEL">Disponível</option>
              <option value="ALUGADO">Alugado</option>
              <option value="VENDIDO">Vendido</option>
            </select>
          </div>

          {/* Botões de Ação */}
          <div className="pt-4 flex items-center justify-end gap-3 border-t border-stone-100">
            <Link
              href="/imovel"
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