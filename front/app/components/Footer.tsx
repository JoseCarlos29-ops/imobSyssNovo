export default function Footer() {
    const anoAtual = new Date().getFullYear();
  
    return (
      <footer className="w-full bg-stone-900 text-stone-300 border-t border-stone-800 mt-auto">
        <div className="max-w-7xl mx-auto px-8 py-5 flex flex-col sm:flex-row items-center justify-center gap-2 sm:gap-6 text-xs font-medium text-center">
          <p className="text-stone-400">
            &copy; {anoAtual}{" "}
            <span className="text-white font-semibold mx-1">
              Imob<span className="text-amber-500">Syss</span>
            </span>
            — Todos os direitos reservados.
          </p>
  
          <span className="hidden sm:inline text-stone-600">•</span>
  
          <span className="text-stone-500 text-[11px] uppercase tracking-wider">
            Painel Administrativo
          </span>
        </div>
      </footer>
    );
  }