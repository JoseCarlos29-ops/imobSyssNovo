package org.example.imobsyss2.application.dtos;


public record CriarAdminRequest(String nome, String email, String senha, String secretKey) {
}