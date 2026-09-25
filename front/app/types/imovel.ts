export class Imovel{
    constructor(
        public id: number | null,
        public endereco: string,
        public tipo: string,
        public valor: number,
        public area: number,
        public quartos: number,
        public banheiros: number,
        public descricao: string,
        public status: string,
        public proprietario: string

    ){}
}

export interface ImovelFormProps{
    imovelExistente?:Imovel
}