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
        public proprietario: string,
        // localidade preenchida pelo ViaCEP (a partir do CEP)
        public cep: string = "",
        public bairro: string = "",
        public cidade: string = "",
        public uf: string = ""

    ){}
}

export interface ImovelFormProps{
    imovelExistente?:Imovel
}