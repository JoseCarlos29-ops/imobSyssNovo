package org.example.imobsyss2.application.dtos;

import org.example.imobsyss2.domain.entities.TipodeImovel;

public record TipoImovelResponse(Long id, String nome, String descricao) {

    public TipoImovelResponse(TipodeImovel tipoEntidade) {
        this(
                tipoEntidade.getId(),
                tipoEntidade.getNome(),
                tipoEntidade.getDescricao()
        );
    }
}
