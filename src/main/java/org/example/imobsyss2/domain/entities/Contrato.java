package org.example.imobsyss2.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.imobsyss2.application.dtos.EnumStatusContrato;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    private String tipo;

    private double valorContrato;

    private double valorComissao;

    private double dataContrato;

    private EnumStatusContrato statusContrato;

    @Column(name = "empresa_id")
    private Long empresaId;

}
