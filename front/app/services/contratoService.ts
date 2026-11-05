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
    const dados = await api.delete(`/contratos/${id}/excluirContrato`);
    return dados.status;
}

const proximoStatusContrato = (statusAtual: string) => {
    if (statusAtual === "ATIVO") return "ENCERRADO";
    if (statusAtual === "ENCERRADO") return "CANCELADO";
    return "ATIVO";
}

export async function atualizarStatusContrato(contrato: Contrato): Promise<void> {

    const novoStatus = { statusContrato: proximoStatusContrato(contrato.statusContrato) };

    const dadosRetorno = await api.patch(`/contratos/${contrato.id}/AtualizarStatusContrato`, novoStatus);

    if (dadosRetorno.status == 200) {
        alert("Status atualizado com sucesso!");
    } else {
        alert(dadosRetorno.data);
    }
}

export async function consultaContratoPorId(id: number): Promise<Contrato> {
    const resposta = await api.get<Contrato>(`/contratos/${id}`);
    return resposta.data;
}

export async function atualizarContrato(contrato: Contrato): Promise<number> {
    const dadosRetorno = await api.put<number>(`/contratos/${contrato.id}/atualizarContratos`, contrato);
    return dadosRetorno.data;
}

export async function salvarContrato(contrato: Contrato): Promise<number> {
    const dadosRetorno = await api.post<number>("/contratos", contrato);
    return dadosRetorno.data;
}