import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import Footer from "../components/Footer";

export default function SistemaLayout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex min-h-screen w-full bg-stone-100 font-sans antialiased">
      
      {/* Sidebar fixada na lateral ocupando toda a altura */}
      <Sidebar />

      {/* Área à direita do menu */}
      <div className="flex-1 flex flex-col min-w-0 bg-[radial-gradient(ellipse_at_top_left,rgba(120,53,15,0.08),transparent_50%),linear-gradient(to_bottom_right,#fafaf9,#f5f5f4)]">
        
        {/* Topbar no topo da área principal */}
        <Header />

        {/* Conteúdo da página */}
        <main className="flex-1 p-6 md:p-8">
          {children}
        </main>

        {/* Rodapé preto no final */}
        <Footer />

      </div>

    </div>
  );
}