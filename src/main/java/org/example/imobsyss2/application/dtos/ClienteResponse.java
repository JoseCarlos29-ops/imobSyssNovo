package org.example.imobsyss2.application.dtos;

import org.example.imobsyss2.domain.entities.Cliente;

public record ClienteResponse(Long id, String nome, String cpf, String email, String telefone,
                              String endereco, EnumStatusCliente statusCliente) {

    public ClienteResponse(Cliente clienteEntidade) {
        this(
                clienteEntidade.getId(),
                clienteEntidade.getNome(),
                clienteEntidade.getCpf(),
                clienteEntidade.getEmail(),
                clienteEntidade.getTelefone(),
                clienteEntidade.getEndereco(),
                clienteEntidade.getStatusCliente()
        );
    }
}