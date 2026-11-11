import { Endereco } from "../types/endereco";
import api from "./api";

export async function buscarEnderecoPorCep(cep: string): Promise<Endereco> {
    const resposta = await api.get<Endereco>("/endereco/" + cep.replace(/\D/g, ""));
    return resposta.data;
}
