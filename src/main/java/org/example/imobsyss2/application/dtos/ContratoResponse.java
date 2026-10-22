package org.example.imobsyss2.application.dtos;

import org.example.imobsyss2.domain.entities.Contrato;

public record ContratoResponse(Long id, String tipo, double valorContrato, double valorComissao,
                               double dataContrato, EnumStatusContrato statusContrato) {

    public ContratoResponse(Contrato contratoEntidade) {
        this(
                contratoEntidade.getId(),
                contratoEntidade.getTipo(),
                contratoEntidade.getValorContrato(),
                contratoEntidade.getValorComissao(),
                contratoEntidade.getDataContrato(),
                contratoEntidade.getStatusContrato()
        );
    }
}