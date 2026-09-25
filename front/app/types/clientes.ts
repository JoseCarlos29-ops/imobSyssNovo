export class Cliente{
    constructor(
        public id: number | null,
        public nome: string,
        public cpf: string,
        public email: string,
        public telefone: string,
        public endereco: string,
        public statusCliente: string

    ){}
}

export interface ClienteFormProps{
    clienteExistente?:Cliente
}