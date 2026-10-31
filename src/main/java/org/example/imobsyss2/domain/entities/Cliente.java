package org.example.imobsyss2.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.imobsyss2.application.dtos.ClienteRequest;
import org.example.imobsyss2.application.dtos.EnumStatusCliente;
import org.example.imobsyss2.domain.valueObject.CPF;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    @Embedded
    private CPF cpf;

    private String email;

    private String telefone;

    private String endereco;

    private EnumStatusCliente statusCliente = EnumStatusCliente.CLIENTE_ATIVO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
            name = "tb_interesse",
            joinColumns = @JoinColumn(name = "cliente_id"),
            inverseJoinColumns = @JoinColumn(name = "imovel_id")
    )
    private Set<Imovel> imoveis = new HashSet<>();

    public void interessar(Imovel imovel){
        this.imoveis.add(imovel);
    }

    public void setNome(String nome) {
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Nome Invalido!");
        }
        this.nome = nome;
    }

    public void setEmail(String email) {
        if(!email.isEmpty() && !email.contains("@")){
            throw new IllegalArgumentException("Email Invalido!");
        }
        this.email = email;
    }

    public void setTelefone(String telefone) {
        if(telefone == null || telefone.replaceAll("\\D", "").length() < 10 || telefone.replaceAll("\\D", "").length() > 11){
            throw new IllegalArgumentException("Telefone Invalido!");
        }
        this.telefone = telefone;
    }

    public Cliente(ClienteRequest cliente) {
        this.setCpf(new CPF(cliente.cpf()));
        this.setNome(cliente.nome());
        this.setEmail(cliente.email());
        this.setTelefone(cliente.telefone());
        this.setEndereco(cliente.endereco());
    }
}