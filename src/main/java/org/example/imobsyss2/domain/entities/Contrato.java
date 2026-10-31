package org.example.imobsyss2.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.imobsyss2.application.dtos.ContratoRequest;
import org.example.imobsyss2.application.dtos.EnumStatusContrato;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String tipo;

    private double valorContrato;

    private double valorComissao;

    private double dataContrato;

    private EnumStatusContrato statusContrato = EnumStatusContrato.ATIVO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    public void setTipo(String tipo) {
        if(tipo == null || tipo.isBlank()){
            throw new IllegalArgumentException("Tipo Invalido!");
        }
        this.tipo = tipo;
    }

    public void setValorContrato(double valorContrato) {
        if(valorContrato <= 0){
            throw new IllegalArgumentException("Valor do contrato Invalido!");
        }
        this.valorContrato = valorContrato;
    }

    // precisa ser chamado depois do setValorContrato
    public void setValorComissao(double valorComissao) {
        if(valorComissao < 0 || valorComissao > this.valorContrato){
            throw new IllegalArgumentException("Valor da comissao Invalido!");
        }
        this.valorComissao = valorComissao;
    }

    public void setDataContrato(double dataContrato) {
        if(dataContrato <= 0){
            throw new IllegalArgumentException("Data do contrato Invalida!");
        }
        this.dataContrato = dataContrato;
    }

    public Contrato(ContratoRequest contrato) {
        this.setTipo(contrato.tipo());
        this.setValorContrato(contrato.valorContrato());
        this.setValorComissao(contrato.valorComissao());
        this.setDataContrato(contrato.dataContrato());
    }
}