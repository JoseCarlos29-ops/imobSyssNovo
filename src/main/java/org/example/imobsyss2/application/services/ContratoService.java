package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.*;
import org.example.imobsyss2.domain.entities.Empresa;
import org.example.imobsyss2.domain.entities.Contrato;
import org.example.imobsyss2.domain.repository.EmpresaRepository;
import org.example.imobsyss2.domain.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Empresa buscarEmpresaDoUsuarioLogado() {
        var empresaId = getUsuarioLogado().empresa_id();
        if (empresaId == null) {
            return null;
        }
        return empresaRepository.findById(empresaId).orElse(null);
    }

    public List<ContratoResponse> listarTodos() {

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return contratoRepository.findAll()
                    .stream()
                    .map(ContratoResponse::new)
                    .toList();
        }

        return contratoRepository.getContratosByEmpresa_Id(usuarioLogado.empresa_id())
                .stream()
                .map(ContratoResponse::new)
                .toList();
    }

    public ContratoResponse buscarPorId(Long id) {

        var contrato = contratoRepository.findById(id).orElse(null);
        if(contrato == null){
            return null;
        }

        return new ContratoResponse(contrato);
    }

    public ContratoResponse criar(ContratoRequest request) {

        Contrato contratoBanco = new Contrato(request);
        contratoBanco.setEmpresa(buscarEmpresaDoUsuarioLogado());
        contratoRepository.save(contratoBanco);

        return new ContratoResponse(contratoBanco);
    }

    public boolean atualizarStatusContrato(Long id, AtualizarStatusContrato request) {

        Contrato contratoBanco = contratoRepository.findById(id).orElse(null);
        if(contratoBanco != null){
            contratoBanco.setStatusContrato(request.statusContrato());
            contratoRepository.save(contratoBanco);
            return true;
        }
        return false;
    }

    public ContratoResponse atualizar(Long id, ContratoRequest request) {

        Contrato contratoBanco = contratoRepository.findById(id).orElse(null);
        if(contratoBanco != null){
            contratoBanco.setTipo(request.tipo());
            contratoBanco.setValorContrato(request.valorContrato());
            contratoBanco.setValorComissao(request.valorComissao());
            contratoBanco.setDataContrato(request.dataContrato());
            contratoRepository.save(contratoBanco);
            return new ContratoResponse(contratoBanco);
        }

        return null;
    }

    public boolean excluir(Long id) {

        return atualizarStatusContrato(id, new AtualizarStatusContrato(EnumStatusContrato.CANCELADO));
    }
}