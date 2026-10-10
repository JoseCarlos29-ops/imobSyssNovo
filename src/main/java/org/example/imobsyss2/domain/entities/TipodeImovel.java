package org.example.imobsyss2.domain.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class TipodeImovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;       // Rural, Residencial, Comercial, Industrial...

    private String descricao;
}
