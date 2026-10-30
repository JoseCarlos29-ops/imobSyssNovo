package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusContrato;
import org.example.imobsyss2.application.dtos.ContratoRequest;
import org.example.imobsyss2.application.dtos.ContratoResponse;
import org.example.imobsyss2.application.dtos.EnumStatusContrato;
import org.example.imobsyss2.application.dtos.UsuarioLogado;
import org.example.imobsyss2.domain.entities.Contrato;
import org.example.imobsyss2.domain.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<ContratoResponse> listarTodos() {
        var usuarioLogado = getUsuarioLogado();
        return contratoRepository.findByEmpresaId(usuarioLogado.empresa_id()).stream()
                .map(ContratoResponse::new)
                .toList();
    }

    public ContratoResponse buscarPorId(Long id) {
        var usuarioLogado = getUsuarioLogado();
        return contratoRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id())
                .map(ContratoResponse::new)
                .orElse(null);
    }

    public ContratoResponse criar(ContratoRequest request) {
        var usuarioLogado = getUsuarioLogado();
        Contrato contrato = new Contrato();
        contrato.setTipo(request.tipo());
        contrato.setDataContrato(request.dataContrato());
        contrato.setValorContrato(request.valorContrato());
        contrato.setValorComissao(request.valorComissao());
        contrato.setStatusContrato(EnumStatusContrato.ATIVO);
        contrato.setEmpresaId(usuarioLogado.empresa_id());

        return new ContratoResponse(contratoRepository.save(contrato));
    }

    public boolean atualizarStatusContrato(Long id, AtualizarStatusContrato request) {
        var usuarioLogado = getUsuarioLogado();
        Contrato contratoBanco = contratoRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);
        if (contratoBanco == null) {
            return false;
        }
        contratoBanco.setStatusContrato(request.statusContrato());
        contratoRepository.save(contratoBanco);
        return true;
    }

    public ContratoResponse atualizar(Long id, ContratoRequest request) {
        var usuarioLogado = getUsuarioLogado();
        Contrato contratoBanco = contratoRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);
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
        var usuarioLogado = getUsuarioLogado();
        Contrato contratoBanco = contratoRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);
        if (contratoBanco == null) {
            return false;
        }
        contratoBanco.setStatusContrato(EnumStatusContrato.CANCELADO);
        contratoRepository.save(contratoBanco);
        return true;
    }
}