package org.example.imobsyss2.application.dtos;

public record ClienteRequest(String nome, String cpf, String email, String telefone, String endereco) {
}