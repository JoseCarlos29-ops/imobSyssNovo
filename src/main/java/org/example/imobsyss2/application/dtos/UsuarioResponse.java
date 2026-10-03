package org.example.imobsyss2.application.dtos;

import org.example.imobsyss2.domain.entities.EnumStatusUsuario;
import org.example.imobsyss2.domain.entities.Usuario;

public record UsuarioResponse(Long id, String nome, String cpf, String email, EnumStatusUsuario status) {

    public UsuarioResponse(Usuario usuarioEntidade){
        this(
                usuarioEntidade.getId(),
                usuarioEntidade.getNome(),
                usuarioEntidade.getCpf(),
                usuarioEntidade.getEmail(),
                usuarioEntidade.getStatus()
        );
    }
}
