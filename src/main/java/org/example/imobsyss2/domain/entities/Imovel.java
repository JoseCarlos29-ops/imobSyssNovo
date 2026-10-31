package org.example.imobsyss2.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.imobsyss2.application.dtos.EnumStatusImovel;
import org.example.imobsyss2.application.dtos.ImovelRequest;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String endereco;

    private String tipo;

    private double valor;

    private double area;

    private int quartos;

    private int banheiros;

    private String descricao;

    private EnumStatusImovel status = EnumStatusImovel.DISPONIVEL;

    private String proprietario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @ManyToMany(mappedBy = "imoveis")
    private Set<Cliente> clientes = new HashSet<>();

    public void setEndereco(String endereco) {
        if(endereco == null || endereco.isBlank()){
            throw new IllegalArgumentException("Endereco Invalido!");
        }
        this.endereco = endereco;
    }

    public void setTipo(String tipo) {
        if(tipo == null || tipo.isBlank()){
            throw new IllegalArgumentException("Tipo Invalido!");
        }
        this.tipo = tipo;
    }

    public void setValor(double valor) {
        if(valor <= 0){
            throw new IllegalArgumentException("Valor Invalido!");
        }
        this.valor = valor;
    }

    public void setArea(double area) {
        if(area <= 0){
            throw new IllegalArgumentException("Area Invalida!");
        }
        this.area = area;
    }

    public void setQuartos(int quartos) {
        if(quartos < 0){
            throw new IllegalArgumentException("Quantidade de quartos Invalida!");
        }
        this.quartos = quartos;
    }

    public void setBanheiros(int banheiros) {
        if(banheiros < 0){
            throw new IllegalArgumentException("Quantidade de banheiros Invalida!");
        }
        this.banheiros = banheiros;
    }

    public void setProprietario(String proprietario) {
        if(proprietario == null || proprietario.isBlank()){
            throw new IllegalArgumentException("Proprietario Invalido!");
        }
        this.proprietario = proprietario;
    }

    public Imovel(ImovelRequest imovel) {
        this.setEndereco(imovel.endereco());
        this.setTipo(imovel.tipo());
        this.setValor(imovel.valor());
        this.setArea(imovel.area());
        this.setQuartos(imovel.quartos());
        this.setBanheiros(imovel.banheiros());
        this.setDescricao(imovel.descricao());
        this.setProprietario(imovel.proprietario());
    }
}