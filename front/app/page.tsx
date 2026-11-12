import Link from "next/link";


export default function Home() {
  return (
    <>
    {/* Estilos embutidos (paleta igual à do login e das telas do sistema: stone + amber + preto) */}
    <style dangerouslySetInnerHTML={{ __html: `
      :root {
          --primary: #000000;          /* botões pretos, como no login */
          --primary-dark: #1c1917;     /* stone-900 */
          --primary-light: #f5f5f4;    /* stone-100 */
          --accent: #78350f;           /* amber-900 */
          --accent-deep: #451a03;      /* amber-950 */
          --border: #e7e5e4;           /* stone-200 */
          --text-main: #1c1917;        /* stone-900 */
          --text-muted: #57534e;       /* stone-600 */
          --bg-color: #f5f5f4;         /* stone-100 */
          --white: #ffffff;
          --transition: all 0.3s ease;
      }

      * {
          margin: 0;
          padding: 0;
          box-sizing: border-box;
      }

      body {
          font-family: 'Inter', sans-serif;
          background-color: var(--bg-color);
          background-image:
            radial-gradient(ellipse at top left, rgba(120, 53, 15, 0.15), transparent 50%),
            radial-gradient(ellipse at bottom right, rgba(69, 26, 3, 0.1), transparent 50%),
            linear-gradient(to bottom right, #fafaf9, #f5f5f4);
          background-attachment: fixed;
          color: var(--text-main);
          line-height: 1.6;
      }

      a {
          text-decoration: none;
          color: inherit;
      }

      header {
          background-color: var(--white);
          border-bottom: 1px solid rgba(231, 229, 228, 0.6);
          box-shadow: 0 2px 10px rgba(0, 0, 0, 0.04);
          position: fixed;
          top: 0;
          left: 0;
          width: 100%;
          z-index: 1000;
      }

      .nav-container {
          max-width: 1200px;
          margin: 0 auto;
          padding: 1.2rem 2rem;
          display: flex;
          justify-content: space-between;
          align-items: center;
      }

      .logo {
          font-size: 1.5rem;
          font-weight: 800;
          color: var(--primary-dark);
          display: flex;
          align-items: center;
          gap: 0.5rem;
          letter-spacing: -0.03em;
      }

      .logo span {
          color: var(--accent);
      }

      .nav-links {
          display: flex;
          gap: 2rem;
          list-style: none;
          align-items: center;
      }

      .nav-links a:not(.btn-login) {
          color: var(--text-muted);
          font-weight: 600;
          transition: var(--transition);
      }

      .nav-links a:not(.btn-login):hover {
          color: var(--accent);
      }

      .btn-login {
          background-color: var(--primary);
          color: var(--white);
          padding: 0.6rem 1.5rem;
          border-radius: 12px;
          font-weight: 600;
          transition: var(--transition);
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      }

      .btn-login:hover {
          background-color: var(--primary-dark);
          transform: translateY(-2px);
      }

      .hero {
          padding: 10rem 2rem 6rem;
          max-width: 1200px;
          margin: 0 auto;
          display: grid;
          grid-template-columns: 1fr 1fr;
          gap: 4rem;
          align-items: center;
      }

      .hero-content h1 {
          font-size: 3.2rem;
          line-height: 1.2;
          color: var(--primary);
          margin-bottom: 1.5rem;
          letter-spacing: -0.03em;
      }

      .hero-content h1 span {
          color: var(--accent);
      }

      .hero-content p {
          font-size: 1.1rem;
          color: var(--text-muted);
          margin-bottom: 2.5rem;
      }

      .hero-buttons {
          display: flex;
          gap: 1rem;
      }

      .btn-primary {
          background-color: var(--primary);
          color: var(--white);
          padding: 0.9rem 2rem;
          border-radius: 12px;
          font-weight: 700;
          transition: var(--transition);
          box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
      }

      .btn-primary:hover {
          background-color: var(--primary-dark);
      }

      .hero-card {
          background: linear-gradient(135deg, var(--primary-dark), var(--accent-deep));
          border-radius: 24px;
          padding: 3rem;
          color: var(--white);
          box-shadow: 0 20px 60px -10px rgba(0, 0, 0, 0.25);
          position: relative;
          overflow: hidden;
      }

      .hero-card h3 {
          font-size: 1.8rem;
          margin-bottom: 1rem;
      }

      .hero-card p {
          opacity: 0.9;
      }

      .about {
          background-color: var(--white);
          border-top: 1px solid var(--border);
          padding: 6rem 2rem;
      }

      .about-container {
          max-width: 900px;
          margin: 0 auto;
          text-align: center;
      }

      .about h2 {
          font-size: 2.5rem;
          color: var(--primary);
          margin-bottom: 1.5rem;
          letter-spacing: -0.03em;
      }

      .about p {
          font-size: 1.15rem;
          color: var(--text-muted);
          line-height: 1.8;
          margin-bottom: 3rem;
      }

      .features-grid {
          display: grid;
          grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
          gap: 2rem;
          text-align: left;
          margin-top: 3rem;
      }

      .feature-box {
          background-color: var(--primary-light);
          border: 1px solid var(--border);
          padding: 2rem;
          border-radius: 20px;
          transition: var(--transition);
      }

      .feature-box:hover {
          transform: translateY(-5px);
          border-color: rgba(120, 53, 15, 0.3);
      }

      .feature-box h4 {
          color: var(--accent);
          margin-bottom: 0.8rem;
          font-size: 1.2rem;
      }

      .feature-box p {
          font-size: 0.95rem;
          margin-bottom: 0;
      }

      footer {
          background-color: var(--primary-dark);
          color: var(--white);
          text-align: center;
          padding: 2rem;
          font-size: 0.9rem;
      }

      @media (max-width: 768px) {
          .hero {
              grid-template-columns: 1fr;
              text-align: center;
              padding-top: 8rem;
          }
          .hero-buttons {
              justify-content: center;
          }
          .nav-links {
              display: none;
          }
          .hero-content h1 {
              font-size: 2.5rem;
          }
      }
    `}} />

    {/* Cabeçalho */}
    <header>
      <div className="nav-container">
        <a href="#" className="logo">🏠 Imob<span>Syss</span></a>
        <ul className="nav-links">
          <li><a href="#sobre">Nossa História</a></li>
          <li><a href="#recursos">Recursos</a></li>
          <li>
            <Link href="/login" className="btn-login">Entrar</Link>
          </li>
        </ul>
      </div>
    </header>

    {/* Seção Hero */}
    <main>
      <section className="hero">
        <div className="hero-content">
          <h1>Gestão imobiliária <span>simples e segura</span></h1>
          <p>Cadastro de clientes, imóveis e contratos, com controle de usuários e acessos, em um só lugar.</p>
          <div className="hero-buttons">
            <a href="#sobre" className="btn-primary">Conhecer o Projeto</a>
          </div>
        </div>
        <div className="hero-card">
          <h3>ImobSyss</h3>
          <p>Desenvolvido para organizar a rotina da imobiliária, com segurança, agilidade e autonomia.</p>
        </div>
      </section>

      {/* Seção Nossa História */}
      <section className="about" id="sobre">
        <div className="about-container">
          <h2>Nossa História</h2>
          <p>O <strong>ImobSyss</strong> nasceu como um projeto de gestão imobiliária, pensado para ter facilidade de cadastro, controle de perfil de usuário e uma gestão de acessos simples e segura.</p>

          <div className="features-grid" id="recursos">
            <div className="feature-box">
              <h4>Fácil Cadastro</h4>
              <p>Cadastro rápido e intuitivo de clientes, imóveis e contratos.</p>
            </div>
            <div className="feature-box">
              <h4>Controle de Perfil</h4>
              <p>Gestão centralizada dos dados de cada empresa e de cada usuário.</p>
            </div>
            <div className="feature-box">
              <h4>Gestão de Acessos</h4>
              <p>Controle de acesso por perfil: administrador e corretor.</p>
            </div>
          </div>
        </div>
      </section>
    </main>

    {/* Rodapé */}
    <footer>
      <p>&copy; 2026 ImobSyss. Projeto Acadêmico.</p>
    </footer>
  </>
  );
}