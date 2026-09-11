import Link from "next/link";

export default function Imovel(){
    return (
        <div>
            <div>
                <h1>Gestão de usuários</h1>

                <Link href="/usuarios/novo">
                    Novo usuário
                </Link>
            </div>

            <div>
                <div>
                    <table>
                        <thead>
                            <tr>
                                <th>Nome</th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr>
                                <td>José</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
}