export default function Header() {
    return (
      <header className="w-full bg-stone-900 border-b border-stone-800 px-8 py-4 flex items-center justify-between text-white shadow-sm">
        {/* Bloco de informações do usuário */}
        <div className="flex items-center gap-3">
          <div className="w-9 h-9 rounded-full bg-stone-800 border border-stone-700 flex items-center justify-center text-amber-500 shadow-sm">
            <svg 
              xmlns="http://www.w3.org/2000/svg" 
              className="w-5 h-5 stroke-current fill-none stroke-[2]"
              viewBox="0 0 24 24" 
              strokeLinecap="round" 
              strokeLinejoin="round"
            >
              <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2" />
              <circle cx="12" cy="7" r="4" />
            </svg>
          </div>
          
          <div className="flex flex-col">
            <span className="text-[10px] text-stone-400 font-semibold uppercase tracking-wider">
              Usuário Conectado
            </span>
            <span className="text-sm font-semibold text-white tracking-tight">
              José
            </span>
          </div>
        </div>
  
        {/* Botão de Sair */}
        <button className="px-4 py-2 bg-stone-800 hover:bg-stone-700 text-stone-200 hover:text-white text-xs uppercase tracking-wider font-semibold rounded-xl border border-stone-700 transition-all duration-200 active:scale-95">
          Sair
        </button>
      </header>
    );
  }