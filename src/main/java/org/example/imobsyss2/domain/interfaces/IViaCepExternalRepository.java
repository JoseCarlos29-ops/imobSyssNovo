package org.example.imobsyss2.domain.interfaces;

import org.example.imobsyss2.infra.dto.ViaCepResponse;

public interface IViaCepExternalRepository {

        ViaCepResponse buscarEnderecoViaCepPorCep(String cep);


}
