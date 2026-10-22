package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusContrato;
import org.example.imobsyss2.domain.entities.Contrato;
import org.example.imobsyss2.application.dtos.EnumStatusContrato;
import org.example.imobsyss2.domain.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    public List<Contrato> listarTodos() {

        return contratoRepository.findAll();
    }

    public Contrato buscarPorId(Long id) {

        return contratoRepository.findById(id).orElse(null);
    }

    public Contrato criar(Contrato contrato) {

        return contratoRepository.save(contrato);
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

    public Contrato atualizar(Long id, Contrato contrato) {

        Contrato contratoBanco = contratoRepository.findById(id).orElse(null);

        if (contratoBanco == null) {
            return null;
        }

        contratoBanco.setTipo(contrato.getTipo());
        contratoBanco.setStatusContrato(contrato.getStatusContrato());
        contratoBanco.setDataContrato(contrato.getDataContrato());
        contratoBanco.setValorContrato(contrato.getValorContrato());
        contratoBanco.setValorComissao(contrato.getValorComissao());

        return contratoRepository.save(contratoBanco);
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