package org.example.imobsyss2.application.dtos;

import org.example.imobsyss2.domain.entities.EnumStatusUsuario;

public record AtualizarStatusRequest(EnumStatusUsuario status) {
}
