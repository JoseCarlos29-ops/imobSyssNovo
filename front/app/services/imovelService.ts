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
    const dados = await api.delete(`/imoveis/${id}/excluirImovel`);
    return dados.status;
}

const proximoStatusImovel = (statusAtual: string) => {
    if (statusAtual === "DISPONIVEL") return "ALUGADO";
    if (statusAtual === "ALUGADO") return "VENDIDO";
    return "DISPONIVEL";
}

export async function atualizarStatusImovel(imovel: Imovel): Promise<void> {

    const novoStatus = { statusImovel: proximoStatusImovel(imovel.status) };

    const dadosRetorno = await api.patch(`/imoveis/${imovel.id}/statusImovel`, novoStatus);

    if (dadosRetorno.status == 200) {
        alert("Status atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
    }
}

export async function consultaImovelPorId(id: number): Promise<Imovel> {
    const resposta = await api.get<Imovel>(`/imoveis/${id}`);
    return resposta.data;
}

export async function atualizarImovel(imovel: Imovel): Promise<number> {
    const dadosRetorno = await api.put<number>(`/imoveis/${imovel.id}/atualizarImovel`, imovel);
    return dadosRetorno.data;
}

export async function salvarImovel(imovel: Imovel): Promise<number> {
    const dadosRetorno = await api.post<number>("/imoveis", imovel);
    return dadosRetorno.data;
}