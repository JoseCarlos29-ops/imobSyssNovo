import type { Metadata } from "next";
import "./globals.css";
import StoreProvider from "./redux/StoreProvider";

export const metadata: Metadata = {
  title: "Meu Projeto",
  description: "Gerenciado com Redux",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="pt-BR" className="h-full">
      <body className="h-full">
        <StoreProvider>
          {children}
        </StoreProvider>
      </body>
    </html>
  );
}