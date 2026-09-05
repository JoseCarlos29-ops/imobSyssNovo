"use client";

import React, { useState, useEffect } from "react";
import Link from "next/link";

export default function Home() {
  // Estado para o carrossel de fotos (troca a cada 8 segundos)
  const [currentSlide, setCurrentSlide] = useState(0);

  const slides = [
    {
      url: "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?auto=format&fit=crop&w=1200&q=80",
      title: "Residências de Alto Padrão",
      subtitle: "Encontre o lar ideal com tecnologia e praticidade."
    },
    {
      url: "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?auto=format&fit=crop&w=1200&q=80",
      title: "Gestão Ágil de Locações",
      subtitle: "Processos 100% digitais para proprietários e inquilinos."
    },
    {
      url: "https://images.unsplash.com/photo-1600607687939-ce8a6c25118c?auto=format&fit=crop&w=1200&q=80",
      title: "Empreendimentos Modernos",
      subtitle: "As melhores oportunidades do mercado imobiliário."
    }
  ];

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentSlide((prev) => (prev + 1) % slides.length);
    }, 8000); // 8000ms = 8 segundos

    return () => clearInterval(timer);
  }, [slides.length]);

  return (
    <div style={{
      background: "radial-gradient(circle at center, #ffffff 40%, #f5efe6 80%, #e8dccb 100%)",
      color: "#0a0a0a",
      fontFamily: "system-ui, -apple-system, sans-serif",
      minHeight: "100vh",
      lineHeight: 1.6,
      margin: 0,
      padding: 0
    }}>
      {/* Header / Nav */}
      <header style={{
        position: "fixed",
        top: 0,
        width: "100%",
        zIndex: 100,
        backdropFilter: "blur(12px)",
        background: "rgba(255, 255, 255, 0.85)",
        borderBottom: "1px solid rgba(0, 0, 0, 0.08)"
      }}>
        <div style={{
          maxWidth: "1200px",
          margin: "0 auto",
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          padding: "1rem 2rem"
        }}>
          {/* Logo (Fúria da Noite + Casinha) */}
          <div style={{ display: "flex", alignItems: "center", gap: "0.8rem", fontWeight: 800, fontSize: "1.4rem", color: "#0a0a0a" }}>
            <svg width="42" height="42" viewBox="0 0 64 64" fill="none" xmlns="http://www.w3.org/2000/svg">
              {/* Fundo escuro do escudo da logo */}
              <rect width="64" height="64" rx="14" fill="#0a0a0a" />
              
              {/* Casinha */}
              <path d="M20 48V32L32 22L44 32V48H20Z" stroke="#ffffff" strokeWidth="2.5" strokeLinejoin="round" fill="none" />
              <path d="M28 48V38H36V48" stroke="#ffffff" strokeWidth="2" strokeLinejoin="round" fill="none" />
              
              {/* Cabeça e Orelhas do Fúria da Noite (Banguela) no topo da casa */}
              <path d="M16 22C14 14 22 12 25 18C28 14 36 14 39 18C42 12 50 14 48 22C46 27 38 29 32 29C26 29 18 27 16 22Z" fill="#0a0a0a" stroke="#ffffff" strokeWidth="1.5" />
              {/* Olhos Verdes Marcantes do Fúria da Noite */}
              <ellipse cx="25" cy="22" rx="3.5" ry="2" fill="#76c893" transform="rotate(-10 25 22)" />
              <ellipse cx="39" cy="22" rx="3.5" ry="2" fill="#76c893" transform="rotate(10 39 22)" />
              {/* Pupilas Fendas */}
              <ellipse cx="25" cy="22" rx="1" ry="1.8" fill="#0a0a0a" />
              <ellipse cx="39" cy="22" rx="1" ry="1.8" fill="#0a0a0a" />
            </svg>

            <span>
              Imob<span style={{ color: "#8c6d46" }}>Syss</span>
            </span>
          </div>
          
          {/* Links de Redirecionamento */}
          <nav style={{ display: "flex", gap: "1.8rem", alignItems: "center" }}>
            <a href="#solucoes" style={{ color: "#4a4a4a", textDecoration: "none", fontSize: "0.95rem", fontWeight: 500 }}>Soluções</a>
            <a href="#historia" style={{ color: "#4a4a4a", textDecoration: "none", fontSize: "0.95rem", fontWeight: 500 }}>Nossa História</a>
            <a href="#desafios" style={{ color: "#4a4a4a", textDecoration: "none", fontSize: "0.95rem", fontWeight: 500 }}>Desafios</a>
            <a href="#recursos" style={{ color: "#4a4a4a", textDecoration: "none", fontSize: "0.95rem", fontWeight: 500 }}>Recursos</a>
          </nav>

          {/* Botões Entrar */}
          <div style={{ display: "flex", gap: "0.8rem", alignItems: "center" }}>
            <Link 
              href="/login"
              style={{
                background: "transparent",
                border: "1.5px solid #0a0a0a",
                color: "#0a0a0a",
                padding: "0.55rem 1.2rem",
                borderRadius: "8px",
                fontWeight: 600,
                cursor: "pointer",
                textDecoration: "none"
              }}
            >
              Entrar
            </Link>
          </div>
        </div>
      </header>

      <main>
        {/* Hero Section */}
        <section style={{
          maxWidth: "1200px",
          margin: "0 auto",
          padding: "9rem 2rem 3rem",
          textAlign: "center"
        }}>
          <h1 style={{ fontSize: "3.2rem", fontWeight: 800, letterSpacing: "-1px", marginBottom: "1.2rem", color: "#0a0a0a" }}>
            A plataforma definitiva para <br />
            <span style={{ color: "#8c6d46" }}>
              gestão imobiliária moderna
            </span>
          </h1>
          <p style={{ fontSize: "1.15rem", color: "#4a4a4a", maxWidth: "680px", margin: "0 auto 2rem" }}>
            Simplifique operações de compra, venda e locação com uma tecnologia desenhada para acelerar negócios e dominar o mercado.
          </p>
        </section>

        {/* Carrossel de Fotos (Troca a cada 8 segundos) */}
        <section style={{
          maxWidth: "1100px",
          margin: "0 auto 5rem",
          padding: "0 2rem"
        }}>
          <div style={{
            position: "relative",
            width: "100%",
            height: "420px",
            borderRadius: "16px",
            overflow: "hidden",
            boxShadow: "0 20px 40px rgba(0, 0, 0, 0.12)"
          }}>
            {/* Imagem do Slide */}
            <div style={{
              width: "100%",
              height: "100%",
              backgroundImage: `linear-gradient(to top, rgba(0,0,0,0.7) 0%, rgba(0,0,0,0) 60%), url(${slides[currentSlide].url})`,
              backgroundSize: "cover",
              backgroundPosition: "center",
              transition: "background-image 0.8s ease-in-out",
              display: "flex",
              flexDirection: "column",
              justifyContent: "flex-end",
              padding: "2.5rem",
              color: "#ffffff"
            }}>
              <h2 style={{ fontSize: "2rem", fontWeight: 700, marginBottom: "0.4rem" }}>
                {slides[currentSlide].title}
              </h2>
              <p style={{ fontSize: "1.1rem", opacity: 0.9 }}>
                {slides[currentSlide].subtitle}
              </p>
            </div>

            {/* Controles de Indicadores (Bolinhas) */}
            <div style={{
              position: "absolute",
              bottom: "1.5rem",
              right: "2rem",
              display: "flex",
              gap: "0.5rem"
            }}>
              {slides.map((_, index) => (
                <button
                  key={index}
                  onClick={() => setCurrentSlide(index)}
                  style={{
                    width: index === currentSlide ? "28px" : "10px",
                    height: "10px",
                    borderRadius: "5px",
                    border: "none",
                    backgroundColor: index === currentSlide ? "#8c6d46" : "rgba(255, 255, 255, 0.6)",
                    cursor: "pointer",
                    transition: "all 0.3s ease"
                  }}
                />
              ))}
            </div>
          </div>
        </section>

        {/* Seção Soluções */}
        <section id="solucoes" style={{
          maxWidth: "1200px",
          margin: "0 auto",
          padding: "4rem 2rem",
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))",
          gap: "2rem"
        }}>
          <div style={{
            background: "rgba(255, 255, 255, 0.9)",
            border: "1px solid rgba(0, 0, 0, 0.08)",
            borderRadius: "12px",
            padding: "2.5rem",
            boxShadow: "0 10px 30px rgba(0,0,0,0.03)"
          }}>
            <h3 style={{ fontSize: "1.3rem", marginBottom: "0.8rem", color: "#0a0a0a" }}>Gestão de Vendas</h3>
            <p style={{ color: "#5a5a5a", fontSize: "0.95rem" }}>Acompanhe pipelines de negociação, propostas e fechamentos de contratos em tempo real com transparência total.</p>
          </div>

          <div style={{
            background: "rgba(255, 255, 255, 0.9)",
            border: "1px solid rgba(0, 0, 0, 0.08)",
            borderRadius: "12px",
            padding: "2.5rem",
            boxShadow: "0 10px 30px rgba(0,0,0,0.03)"
          }}>
            <h3 style={{ fontSize: "1.3rem", marginBottom: "0.8rem", color: "#0a0a0a" }}>Locação Descomplicada</h3>
            <p style={{ color: "#5a5a5a", fontSize: "0.95rem" }}>Automação no controle de aluguéis, reajustes e gestão de vistoria com emissão rápida de documentos.</p>
          </div>

          <div style={{
            background: "rgba(255, 255, 255, 0.9)",
            border: "1px solid rgba(0, 0, 0, 0.08)",
            borderRadius: "12px",
            padding: "2.5rem",
            boxShadow: "0 10px 30px rgba(0,0,0,0.03)"
          }}>
            <h3 style={{ fontSize: "1.3rem", marginBottom: "0.8rem", color: "#0a0a0a" }}>Multinegócios</h3>
            <p style={{ color: "#5a5a5a", fontSize: "0.95rem" }}>Infraestrutura flexível adaptada para corretores autônomos, médias imobiliárias ou grandes administradoras.</p>
          </div>
        </section>

        {/* Seção Nossa História */}
        <section id="historia" style={{
          maxWidth: "1000px",
          margin: "2rem auto 4rem",
          padding: "3.5rem 2.5rem",
          background: "rgba(255, 255, 255, 0.95)",
          borderRadius: "16px",
          border: "1px solid rgba(140, 109, 70, 0.2)",
          boxShadow: "0 15px 35px rgba(0,0,0,0.04)"
        }}>
          <h2 style={{ fontSize: "2rem", marginBottom: "1.5rem", color: "#0a0a0a" }}>
            Nossa <span style={{ color: "#8c6d46" }}>História</span>
          </h2>
          <p style={{ color: "#4a4a4a", marginBottom: "1.2rem", fontSize: "1.05rem" }}>
            O <strong>ImobSyss</strong> nasceu da percepção de que o mercado imobiliário precisava de uma solução integrada, capaz de unir a complexidade das transações com a simplicidade que a tecnologia exige.
          </p>
        </section>

        {/* Seção Desafios */}
        <section id="desafios" style={{
          maxWidth: "1000px",
          margin: "2rem auto 4rem",
          padding: "3.5rem 2.5rem",
          background: "rgba(255, 255, 255, 0.95)",
          borderRadius: "16px",
          border: "1px solid rgba(140, 109, 70, 0.2)",
          boxShadow: "0 15px 35px rgba(0,0,0,0.04)"
        }}>
          <h2 style={{ fontSize: "2rem", marginBottom: "1.5rem", color: "#0a0a0a" }}>
            Nossos <span style={{ color: "#8c6d46" }}>Desafios</span>
          </h2>
          <p style={{ color: "#4a4a4a", fontSize: "1.05rem" }}>
            Superar a burocracia do setor imobiliário e oferecer agilidade em contratos, vistorias e gestão de carteiras com máxima segurança de dados.
          </p>
        </section>

        {/* Seção Recursos */}
        <section id="recursos" style={{
          maxWidth: "1000px",
          margin: "2rem auto 6rem",
          padding: "3.5rem 2.5rem",
          background: "rgba(255, 255, 255, 0.95)",
          borderRadius: "16px",
          border: "1px solid rgba(140, 109, 70, 0.2)",
          boxShadow: "0 15px 35px rgba(0,0,0,0.04)"
        }}>
          <h2 style={{ fontSize: "2rem", marginBottom: "1.5rem", color: "#0a0a0a" }}>
            Recursos <span style={{ color: "#8c6d46" }}>Exclusivos</span>
          </h2>
          <p style={{ color: "#4a4a4a", fontSize: "1.05rem" }}>
            Painel administrativo em tempo real, relatórios automatizados, integração com banco de dados e controle de acessos customizado.
          </p>
        </section>
      </main>

      {/* Footer */}
      <footer style={{
        borderTop: "1px solid rgba(0, 0, 0, 0.08)",
        textAlign: "center",
        padding: "2rem",
        color: "#6a6a6a",
        fontSize: "0.9rem"
      }}>
        <p>&copy; 2026 ImobSyss. Todos os direitos reservados.</p>
      </footer>
    </div>
  );
}