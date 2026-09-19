export class Contrato{
    constructor(
        public id: number | null,
        public tipo: string,
        public valorContrato: number,
        public valorComissao: number,
        public dataContrato: number,
        public statusContrato: string

    ){}
}