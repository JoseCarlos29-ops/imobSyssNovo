package org.example.imobsyss2.application.dtos;

public record ImovelRequest(String endereco, String tipo, double valor, double area,
                            int quartos, int banheiros, String descricao, String proprietario) {
}