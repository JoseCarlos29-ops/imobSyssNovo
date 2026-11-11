package org.example.imobsyss2.application.services;


import org.example.imobsyss2.domain.interfaces.IViaCepExternalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private IViaCepExternalRepository viaCepExternalRepository;


    public org.example.imobsyss2.application.dto.EnderecoResponse BuscarEndereco(String cep) {

        var enderecoCompleto =  viaCepExternalRepository.buscarEnderecoViaCepPorCep(cep);

        return new org.example.imobsyss2.application.dto.EnderecoResponse( enderecoCompleto.getCep(),
                enderecoCompleto.getLogradouro(),
                enderecoCompleto.getBairro(),
                enderecoCompleto.getLocalidade(),
                enderecoCompleto.getUf());


    }
}
