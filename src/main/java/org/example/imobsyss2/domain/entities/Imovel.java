package org.example.imobsyss2.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.imobsyss2.application.dtos.EnumStatusImovel;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String endereco;

    private String tipo;

    private double valor;

    private double area;

    private int quartos;

    private int banheiros;

    private String descricao;

    private EnumStatusImovel status;

    private String proprietario;

    @Column(name = "empresa_id")
    private Long empresaId;




}
