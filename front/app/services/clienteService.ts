import { Cliente } from "../types/clientes";
import api from "./api";

export async function buscarListaClientes(): Promise<Cliente[]> {
    const dados = await api.get<Cliente[]>("/clientes");

    if (dados.status === 200) {
        return dados.data;
    }

    return [];
}

export async function deletarCliente(id: number): Promise<number> {
    const dados = await api.delete(`/clientes/${id}/excluirCliente`);
    return dados.status;
}

export async function atualizarStatusCliente(cliente: Cliente): Promise<void> {

    var novoStatus = {};
    if (cliente.statusCliente === "CLIENTE_ATIVO") {
        novoStatus = { statusCliente: "BLOQUEADO" }
    } else {
        novoStatus = { statusCliente: "CLIENTE_ATIVO" }
    }

    const dadosRetorno = await api.patch(`/clientes/${cliente.id}/statusCliente`, novoStatus);

    if (dadosRetorno.status == 200) {
        alert("Atulizado status com sucesso!");
    } else {
        alert(dadosRetorno.data);
    }
}

export async function consultaClientePorId(id: number): Promise<Cliente> {
    const resposta = await api.get<Cliente>(`/clientes/${id}`);
    return resposta.data;
}

export async function atualizarCliente(cliente: Cliente): Promise<number> {
    const dadosRetorno = await api.put<number>(`/clientes/${cliente.id}/atualizarCliente`, cliente);
    return dadosRetorno.data;
}

export async function salvarCliente(cliente: Cliente): Promise<number> {
    const dadosRetorno = await api.post<number>("/clientes", cliente);
    return dadosRetorno.data;
}