package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dto.EnderecoResponse;
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

    @Autowired
    private EnderecoService enderecoService;

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

    // Integracao externa (ViaCEP): quando o CEP e informado, a localidade vem da API
    private EnderecoResponse consultarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            return null;
        }
        try {
            return enderecoService.BuscarEndereco(cep);
        } catch (Exception e) {
            throw new IllegalArgumentException("CEP invalido ou nao encontrado!");
        }
    }

    // Se o endereco veio em branco, usa o logradouro retornado pelo ViaCEP
    private ImovelRequest completarEndereco(ImovelRequest request, EnderecoResponse local) {
        if (local != null && (request.endereco() == null || request.endereco().isBlank())) {
            return new ImovelRequest(local.logradouro(), request.tipo(), request.valor(), request.area(),
                    request.quartos(), request.banheiros(), request.descricao(), request.proprietario(),
                    request.cep());
        }
        return request;
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

        var local = consultarCep(request.cep());

        Imovel imovelBanco = new Imovel(completarEndereco(request, local));
        if (local != null) {
            imovelBanco.informarLocalizacao(local.cep(), local.bairro(), local.cidade(), local.uf());
        }
        imovelBanco.setEmpresa(buscarEmpresaDoUsuarioLogado());
        imovelRepository.save(imovelBanco);

        return new ImovelResponse(imovelBanco);
    }

    public boolean atualizarStatus(Long id, AtualizarStatusImovel request) {

        Imovel imovelBanco = buscarNaEmpresa(id).orElse(null);
        if(imovelBanco != null){
            imovelBanco.atualizarStatus(request.statusImovel());
            imovelRepository.save(imovelBanco);
            return true;
        }
        return false;
    }

    public ImovelResponse atualizar(Long id, ImovelRequest request) {

        Imovel imovelBanco = buscarNaEmpresa(id).orElse(null);
        if(imovelBanco != null){
            var local = consultarCep(request.cep());
            var dados = completarEndereco(request, local);

            imovelBanco.setEndereco(dados.endereco());
            imovelBanco.setTipo(dados.tipo());
            imovelBanco.setValor(dados.valor());
            imovelBanco.setArea(dados.area());
            imovelBanco.setQuartos(dados.quartos());
            imovelBanco.setBanheiros(dados.banheiros());
            imovelBanco.setDescricao(dados.descricao());
            imovelBanco.setProprietario(dados.proprietario());
            if (local != null) {
                imovelBanco.informarLocalizacao(local.cep(), local.bairro(), local.cidade(), local.uf());
            }
            imovelRepository.save(imovelBanco);
            return new ImovelResponse(imovelBanco);
        }

        return null;
    }

    public boolean excluir(Long id) {

        return atualizarStatus(id, new AtualizarStatusImovel(EnumStatusImovel.INDISPONIVEL));
    }
}