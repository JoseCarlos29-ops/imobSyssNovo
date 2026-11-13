export class Usuario{
    constructor(
        public id: number | null,
        public nome: string,
        public email: string,
        public status: string,
        public cpf: string,
        public senha: string,
        // ROLE_ADMIN, ROLE_CORRETOR ou ROLE_USER (vem do back em /usuarios/buscarusuariologado)
        public role: string = ""

    ){}
}


export interface UsuarioFormProps{
    usuarioExistente?:Usuario
}