import { Contrato } from "../types/contrato";
import api from "./api";

export async function buscarListaContratos(): Promise<Contrato[]> {
    const dados = await api.get<Contrato[]>("/contratos");

    if (dados.status === 200) {
        return dados.data;
    }

    return [];
}

export async function deletarContrato(id: number): Promise<number> {
    const dados = await api.delete(`/contratos/${id}/excluir`);
    return dados.status;
}

export async function consultaContratoPorId(id: number): Promise<Contrato> {
    const resposta = await api.get<Contrato>(`/contratos/${id}`);
    return resposta.data;
}

export async function atualizarContrato(contrato: Contrato): Promise<number> {
    const dadosRetorno = await api.put<number>(`/contratos/${contrato.id}`, contrato);
    return dadosRetorno.data;
}

export async function salvarContrato(contrato: Contrato): Promise<number> {
    const dadosRetorno = await api.post<number>("/contratos", contrato);
    return dadosRetorno.data;
}