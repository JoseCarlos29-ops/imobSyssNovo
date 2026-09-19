"use client"
import Link from "next/link";
import ContratoForm from "../components/ContratoForm";

export default function CadastrarContrato() {
  return (
    <div className="w-full px-6 py-10 md:px-10 md:py-14">
      <div className="max-w-4xl mx-auto space-y-6">

        {/* Cabeçalho e Navegação */}
        <div>
          <Link
            href="/contratos"
            className="inline-flex items-center gap-2 text-xs font-semibold uppercase tracking-wider text-stone-600 hover:text-black transition-colors duration-200"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="w-4 h-4 stroke-current fill-none stroke-[2]"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M10.5 19.5L3 12m0 0l7.5-7.5M3 12h18"
              />
            </svg>
            Voltar para Listagem
          </Link>

          <div className="mt-4">
            <h1 className="text-2xl sm:text-3xl font-bold text-black tracking-tight">
              Novo Contrato
            </h1>
            <p className="mt-1 text-xs text-stone-500 uppercase tracking-wider">
              Cadastre os dados do novo contrato no sistema
            </p>
          </div>
        </div>

        {/* Container do Formulário */}
        <div className="bg-white border border-stone-200/60 rounded-3xl p-6 sm:p-8 shadow-[0_20px_60px_-10px_rgba(0,0,0,0.05)]">
          <ContratoForm />
        </div>

      </div>
    </div>
  );
}