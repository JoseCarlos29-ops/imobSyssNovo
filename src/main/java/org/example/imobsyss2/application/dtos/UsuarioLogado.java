package org.example.imobsyss2.application.dtos;

import org.example.imobsyss2.domain.entities.Usuario;
import org.example.imobsyss2.domain.valueObject.CPF;

public record UsuarioLogado(Long id, String email, CPF cpf,
                            Long empresa_id,
                            java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities) {

    public UsuarioLogado(Usuario usuario){
        this(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getCpf(),
                1L,
                usuario.getAuthorities()
        );
    }

}
