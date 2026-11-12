import { TipoImovel } from "../types/tipoImovel";
import api from "./api";

export async function buscarTiposImovel(): Promise<TipoImovel[]> {
    const dados = await api.get<TipoImovel[]>("/tipos-imovel");

    if (dados.status === 200) {
        return dados.data;
    }

    return [];
}
