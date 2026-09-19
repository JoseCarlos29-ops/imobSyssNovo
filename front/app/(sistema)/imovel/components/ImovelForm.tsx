import Link from "next/link";

export default function ImovelForm() {
  return (
    <div className="max-w-2xl mx-auto">
      {/* Cabeçalho do Formulário */}
      <div className="mb-6">
        <h1 className="text-2xl md:text-3xl font-bold text-black tracking-tight">
          Novo Imóvel
        </h1>
        <p className="text-xs text-stone-500 uppercase tracking-wider mt-1">
          Cadastre um novo imóvel no sistema
        </p>
      </div>

      {/* Card do Formulário */}
      <form className="bg-white border border-stone-200/60 rounded-3xl p-6 md:p-8 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05)]">
        <div className="space-y-5">

          {/* Descrição */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              Descrição
            </label>
            <input
              type="text"
              name="descricao"
              placeholder="Ex: Apartamento 2 quartos no centro"
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
                defaultValue=""
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
                placeholder="0,00"
                className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200 font-mono"
              />
            </div>
          </div>

          {/* Situação */}
          <div>
            <label className="block text-xs font-semibold text-stone-700 uppercase tracking-wider mb-2">
              Situação
            </label>
            <select
              name="situacao"
              defaultValue=""
              className="w-full px-4 py-3 rounded-xl border border-stone-200 bg-stone-50/50 text-stone-900 text-sm focus:bg-white focus:border-stone-900 focus:outline-none focus:ring-2 focus:ring-stone-900/10 transition-all duration-200"
            >
              <option value="" disabled>Selecione a situação</option>
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