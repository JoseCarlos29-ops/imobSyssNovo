package org.example.imobsyss2.application.dtos;

import org.example.imobsyss2.domain.entities.Imovel;

public record ImovelResponse(Long id, String endereco, String tipo, double valor, double area,
                             int quartos, int banheiros, String descricao,
                             EnumStatusImovel status, String proprietario) {

    public ImovelResponse(Imovel imovelEntidade) {
        this(
                imovelEntidade.getId(),
                imovelEntidade.getEndereco(),
                imovelEntidade.getTipo(),
                imovelEntidade.getValor(),
                imovelEntidade.getArea(),
                imovelEntidade.getQuartos(),
                imovelEntidade.getBanheiros(),
                imovelEntidade.getDescricao(),
                imovelEntidade.getStatus(),
                imovelEntidade.getProprietario()
        );
    }
}