package org.example.imobsyss2.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.imobsyss2.application.dtos.ClienteRequest;
import org.example.imobsyss2.application.dtos.EnumStatusCliente;
import org.example.imobsyss2.domain.valueObject.CPF;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Embedded
    private CPF cpf;

    private String email;

    private String telefone;

    private String endereco;

    private EnumStatusCliente statusCliente = EnumStatusCliente.CLIENTE_ATIVO;

    private Long empresa_id;

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