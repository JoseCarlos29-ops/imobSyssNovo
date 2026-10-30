import { Imovel } from "../types/imovel";
import api from "./api";

export async function buscarListaImoveis(): Promise<Imovel[]> {
    const dados = await api.get<Imovel[]>("/imoveis");

    if (dados.status === 200) {
        return dados.data;
    }

    return [];
}

export async function deletarImovel(id: number): Promise<number> {
    const dados = await api.delete(`/imoveis/${id}/excluir`);
    return dados.status;
}

export async function consultaImovelPorId(id: number): Promise<Imovel> {
    const resposta = await api.get<Imovel>(`/imoveis/${id}`);
    return resposta.data;
}

export async function atualizarImovel(imovel: Imovel): Promise<number> {
    const dadosRetorno = await api.put<number>(`/imoveis/${imovel.id}`, imovel);
    return dadosRetorno.data;
}

export async function salvarImovel(imovel: Imovel): Promise<number> {
    const dadosRetorno = await api.post<number>("/imoveis", imovel);
    return dadosRetorno.data;
}