package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.*;
import org.example.imobsyss2.domain.entities.Empresa;
import org.example.imobsyss2.domain.entities.Imovel;
import org.example.imobsyss2.domain.repository.EmpresaRepository;
import org.example.imobsyss2.domain.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

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


    // Multilocacao: usuario com empresa so enxerga/altera registros da propria empresa.
    // Usuario sem empresa (admin geral) enxerga tudo.
    private Optional<Imovel> buscarNaEmpresa(Long id) {
        var empresaId = getUsuarioLogado().empresa_id();

        if (empresaId == null) {
            return imovelRepository.findById(id);
        }
        return imovelRepository.findByIdAndEmpresa_Id(id, empresaId);
    }

    public List<ImovelResponse> listarTodos() {

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return imovelRepository.findAll()
                    .stream()
                    .map(ImovelResponse::new)
                    .toList();
        }

        return imovelRepository.getImoveisByEmpresa_Id(usuarioLogado.empresa_id())
                .stream()
                .map(ImovelResponse::new)
                .toList();
    }

    public ImovelResponse buscarPorId(Long id) {

        var imovel = buscarNaEmpresa(id).orElse(null);
        if(imovel == null){
            return null;
        }

        return new ImovelResponse(imovel);
    }

    public ImovelResponse criar(ImovelRequest request) {

        Imovel imovelBanco = new Imovel(request);
        imovelBanco.setEmpresa(buscarEmpresaDoUsuarioLogado());
        imovelRepository.save(imovelBanco);

        return new ImovelResponse(imovelBanco);
    }

    public boolean atualizarStatus(Long id, AtualizarStatusImovel request) {

        Imovel imovelBanco = buscarNaEmpresa(id).orElse(null);
        if(imovelBanco != null){
            imovelBanco.setStatus(request.statusImovel());
            imovelRepository.save(imovelBanco);
            return true;
        }
        return false;
    }

    public ImovelResponse atualizar(Long id, ImovelRequest request) {

        Imovel imovelBanco = buscarNaEmpresa(id).orElse(null);
        if(imovelBanco != null){
            imovelBanco.setEndereco(request.endereco());
            imovelBanco.setTipo(request.tipo());
            imovelBanco.setValor(request.valor());
            imovelBanco.setArea(request.area());
            imovelBanco.setQuartos(request.quartos());
            imovelBanco.setBanheiros(request.banheiros());
            imovelBanco.setDescricao(request.descricao());
            imovelBanco.setProprietario(request.proprietario());
            imovelRepository.save(imovelBanco);
            return new ImovelResponse(imovelBanco);
        }

        return null;
    }

    public boolean excluir(Long id) {

        return atualizarStatus(id, new AtualizarStatusImovel(EnumStatusImovel.INDISPONIVEL));
    }
}