package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.*;
import org.example.imobsyss2.domain.entities.Imovel;
import org.example.imobsyss2.domain.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public UsuarioLogado getUsuarioLogado(){
        return (UsuarioLogado) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public List<ImovelResponse> listarTodos() {

        return imovelRepository.findAll()
                .stream()
                .map(ImovelResponse::new)
                .toList();
    }

    public ImovelResponse buscarPorId(Long id) {

        var imovel = imovelRepository.findById(id).orElse(null);
        if(imovel == null){
            return null;
        }

        return new ImovelResponse(imovel);
    }

    public ImovelResponse criar(ImovelRequest request) {

        var usuarioLogado = getUsuarioLogado();

        Imovel imovelBanco = new Imovel(request);
        imovelBanco.setEmpresa_id(usuarioLogado.empresa_id());
        imovelRepository.save(imovelBanco);

        return new ImovelResponse(imovelBanco);
    }

    public boolean atualizarStatus(Long id, AtualizarStatusImovel request) {

        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);
        if(imovelBanco != null){
            imovelBanco.setStatus(request.statusImovel());
            imovelRepository.save(imovelBanco);
            return true;
        }
        return false;
    }

    public ImovelResponse atualizar(Long id, ImovelRequest request) {

        var usuarioLogado = getUsuarioLogado();

        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);
        if(imovelBanco != null){
            imovelBanco.setEndereco(request.endereco());
            imovelBanco.setTipo(request.tipo());
            imovelBanco.setValor(request.valor());
            imovelBanco.setArea(request.area());
            imovelBanco.setQuartos(request.quartos());
            imovelBanco.setBanheiros(request.banheiros());
            imovelBanco.setDescricao(request.descricao());
            imovelBanco.setProprietario(request.proprietario());
            imovelBanco.setEmpresa_id(usuarioLogado.empresa_id());
            imovelRepository.save(imovelBanco);
            return new ImovelResponse(imovelBanco);
        }

        return null;
    }

    public boolean excluir(Long id) {

        return atualizarStatus(id, new AtualizarStatusImovel(EnumStatusImovel.INDISPONIVEL));
    }
}