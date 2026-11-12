package org.example.imobsyss2.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.imobsyss2.application.dtos.CriarTipoImovelRequest;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipodeImovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;       // Rural, Residencial, Comercial, Industrial...

    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome do tipo Invalido!");
        }
        if(nome.trim().length() > 50){
            throw new IllegalArgumentException("Nome do tipo deve ter no maximo 50 caracteres!");
        }
        this.nome = nome.trim();
    }

    public void atualizarDados(String nome, String descricao) {
        this.setNome(nome);
        this.descricao = descricao == null ? "" : descricao.trim();
    }

    public TipodeImovel(CriarTipoImovelRequest tipo) {
        this.atualizarDados(tipo.nome(), tipo.descricao());
    }
}
