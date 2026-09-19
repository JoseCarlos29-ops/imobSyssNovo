import Link from "next/link";

export default function Sidebar() {
  return (
    <aside className="w-64 min-h-screen bg-stone-900 text-stone-100 flex flex-col justify-between p-6 border-r border-stone-800 shrink-0">
      <div>
        {/* Brand/Logo */}
        <div className="text-xl font-bold tracking-tight text-white mb-8 pb-4 border-b border-stone-800 flex items-center gap-2">
          <span className="w-3 h-3 rounded-full bg-amber-600 inline-block" />
          Imob<span className="text-amber-500 font-normal">Syss</span>
        </div>

        {/* Links do Menu */}
        <nav className="flex flex-col gap-2">

        <Link
            href="/usuarios"
            className="flex items-center px-4 py-3 text-sm font-medium rounded-xl text-stone-300 hover:text-white hover:bg-stone-800/80 transition-all duration-200"
          >
            Usuarios
          </Link> 

          <Link
            href="/home"
            className="flex items-center px-4 py-3 text-sm font-medium rounded-xl text-stone-300 hover:text-white hover:bg-stone-800/80 transition-all duration-200"
          >
            Home
          </Link>
          <Link
            href="/imovel"
            className="flex items-center px-4 py-3 text-sm font-medium rounded-xl text-stone-300 hover:text-white hover:bg-stone-800/80 transition-all duration-200"
          >
            Imóvel
          </Link>
          <Link
            href="/clientes"
            className="flex items-center px-4 py-3 text-sm font-medium rounded-xl text-stone-300 hover:text-white hover:bg-stone-800/80 transition-all duration-200"
          >
            Clientes
          </Link>
          <Link
            href="/contratos"
            className="flex items-center px-4 py-3 text-sm font-medium rounded-xl text-stone-300 hover:text-white hover:bg-stone-800/80 transition-all duration-200"
          >
            Contratos
          </Link>
        </nav>
      </div>

      {/* Rodapé do Menu */}
     
    </aside>
  );
}