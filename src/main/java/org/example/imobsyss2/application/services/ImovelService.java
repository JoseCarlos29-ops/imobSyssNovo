package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusImovel;
import org.example.imobsyss2.application.dtos.EnumStatusImovel;
import org.example.imobsyss2.application.dtos.ImovelRequest;
import org.example.imobsyss2.application.dtos.ImovelResponse;
import org.example.imobsyss2.application.dtos.UsuarioLogado;
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
        var usuarioLogado = getUsuarioLogado();
        return imovelRepository.findByEmpresaId(usuarioLogado.empresa_id()).stream()
                .map(ImovelResponse::new)
                .toList();
    }

    public ImovelResponse buscarPorId(Long id) {
        var usuarioLogado = getUsuarioLogado();
        return imovelRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id())
                .map(ImovelResponse::new)
                .orElse(null);
    }

    public ImovelResponse criar(ImovelRequest request) {
        var usuarioLogado = getUsuarioLogado();
        Imovel imovel = new Imovel();
        imovel.setEndereco(request.endereco());
        imovel.setTipo(request.tipo());
        imovel.setValor(request.valor());
        imovel.setArea(request.area());
        imovel.setQuartos(request.quartos());
        imovel.setBanheiros(request.banheiros());
        imovel.setDescricao(request.descricao());
        imovel.setProprietario(request.proprietario());
        imovel.setStatus(EnumStatusImovel.DISPONIVEL);
        imovel.setEmpresaId(usuarioLogado.empresa_id());

        return new ImovelResponse(imovelRepository.save(imovel));
    }

    public boolean atualizarStatus(Long id, AtualizarStatusImovel request) {
        var usuarioLogado = getUsuarioLogado();
        Imovel imovelBanco = imovelRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);
        if (imovelBanco == null) {
            return false;
        }
        imovelBanco.setStatus(request.statusImovel());
        imovelRepository.save(imovelBanco);
        return true;
    }

    public ImovelResponse atualizar(Long id, ImovelRequest request) {
        var usuarioLogado = getUsuarioLogado();
        Imovel imovelBanco = imovelRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);
        if (imovelBanco == null) {
            return null;
        }
        imovelBanco.setEndereco(request.endereco());
        imovelBanco.setTipo(request.tipo());
        imovelBanco.setValor(request.valor());
        imovelBanco.setArea(request.area());
        imovelBanco.setQuartos(request.quartos());
        imovelBanco.setBanheiros(request.banheiros());
        imovelBanco.setDescricao(request.descricao());
        imovelBanco.setProprietario(request.proprietario());
        return new ImovelResponse(imovelRepository.save(imovelBanco));
    }

    public boolean excluir(Long id) {
        var usuarioLogado = getUsuarioLogado();
        Imovel imovelBanco = imovelRepository.findByIdAndEmpresaId(id, usuarioLogado.empresa_id()).orElse(null);
        if (imovelBanco == null) {
            return false;
        }
        imovelBanco.setStatus(EnumStatusImovel.INDISPONIVEL);
        imovelRepository.save(imovelBanco);
        return true;
    }
}