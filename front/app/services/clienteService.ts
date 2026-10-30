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
    const dados = await api.delete(`/clientes/${id}/excluir`);
    return dados.status;
}

export async function consultaClientePorId(id: number): Promise<Cliente> {
    const resposta = await api.get<Cliente>(`/clientes/${id}`);
    return resposta.data;
}

export async function atualizarCliente(cliente: Cliente): Promise<number> {
    const dadosRetorno = await api.put<number>(`/clientes/${cliente.id}`, cliente);
    return dadosRetorno.data;
}

export async function salvarCliente(cliente: Cliente): Promise<number> {
    const dadosRetorno = await api.post<number>("/clientes", cliente);
    return dadosRetorno.data;
}