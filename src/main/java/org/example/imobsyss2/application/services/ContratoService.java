package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusContrato;
import org.example.imobsyss2.application.dtos.ContratoRequest;
import org.example.imobsyss2.application.dtos.ContratoResponse;
import org.example.imobsyss2.application.dtos.EnumStatusContrato;
import org.example.imobsyss2.domain.entities.Contrato;
import org.example.imobsyss2.domain.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    public List<ContratoResponse> listarTodos() {
        return contratoRepository.findAll().stream()
                .map(ContratoResponse::new)
                .toList();
    }

    public ContratoResponse buscarPorId(Long id) {
        return contratoRepository.findById(id)
                .map(ContratoResponse::new)
                .orElse(null);
    }

    public ContratoResponse criar(ContratoRequest request) {
        Contrato contrato = new Contrato();
        contrato.setTipo(request.tipo());
        contrato.setDataContrato(request.dataContrato());
        contrato.setValorContrato(request.valorContrato());
        contrato.setValorComissao(request.valorComissao());
        contrato.setStatusContrato(EnumStatusContrato.ATIVO); // use um valor que exista no seu enum

        return new ContratoResponse(contratoRepository.save(contrato));
    }

    public boolean atualizarStatusContrato(Long id, AtualizarStatusContrato request) {
        Contrato contratoBanco = contratoRepository.findById(id).orElse(null);

        if (contratoBanco == null) {
            return false;
        }

        contratoBanco.setStatusContrato(request.statusContrato());
        contratoRepository.save(contratoBanco);
        return true;
    }

    public ContratoResponse atualizar(Long id, ContratoRequest request) {
        Contrato contratoBanco = contratoRepository.findById(id).orElse(null);

        if (contratoBanco == null) {
            return null;
        }

        contratoBanco.setTipo(request.tipo());
        contratoBanco.setDataContrato(request.dataContrato());
        contratoBanco.setValorContrato(request.valorContrato());
        contratoBanco.setValorComissao(request.valorComissao());

        return new ContratoResponse(contratoRepository.save(contratoBanco));
    }

    public boolean excluir(Long id) {
        Contrato contratoBanco = contratoRepository.findById(id).orElse(null);

        if (contratoBanco == null) {
            return false;
        }

        contratoBanco.setStatusContrato(EnumStatusContrato.CANCELADO);
        contratoRepository.save(contratoBanco);
        return true;
    }
}