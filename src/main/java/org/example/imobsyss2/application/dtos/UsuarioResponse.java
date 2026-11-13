package org.example.imobsyss2.application.dtos;

import org.example.imobsyss2.domain.entities.Usuario;

public record UsuarioResponse(Long id, String nome, String cpf, String email, EnumStatusUsuario status, String role) {

    public UsuarioResponse(Usuario usuarioEntidade) {
        this(
                usuarioEntidade.getId(),
                usuarioEntidade.getNome(),
                usuarioEntidade.getCpf().toString(),
                usuarioEntidade.getEmail(),
                usuarioEntidade.getStatus(),
                usuarioEntidade.getRole()
        );
    }
}