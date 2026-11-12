package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.CriarTipoImovelRequest;
import org.example.imobsyss2.application.dtos.TipoImovelResponse;
import org.example.imobsyss2.application.dtos.UsuarioLogado;
import org.example.imobsyss2.domain.entities.Empresa;
import org.example.imobsyss2.domain.entities.TipodeImovel;
import org.example.imobsyss2.domain.repository.EmpresaRepository;
import org.example.imobsyss2.domain.repository.ImovelRepository;
import org.example.imobsyss2.domain.repository.TipoImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipodeImovelService {

    @Autowired
    private TipoImovelRepository tipoImovelRepository;

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
    private Optional<TipodeImovel> buscarNaEmpresa(Long id) {
        var empresaId = getUsuarioLogado().empresa_id();

        if (empresaId == null) {
            return tipoImovelRepository.findById(id);
        }
        return tipoImovelRepository.findByIdAndEmpresa_Id(id, empresaId);
    }

    // Regra: nome do tipo unico por empresa
    private void validarNomeUnico(String nome, Long idAtual) {
        if (nome == null) {
            return;
        }
        var empresaId = getUsuarioLogado().empresa_id();

        boolean duplicado = tipoImovelRepository.findByNomeIgnoreCase(nome.trim())
                .stream()
                .filter(t -> idAtual == null || !idAtual.equals(t.getId()))
                .anyMatch(t -> Objects.equals(t.getEmpresa() == null ? null : t.getEmpresa().getId(), empresaId));

        if (duplicado) {
            throw new IllegalArgumentException("Ja existe um tipo de imovel com este nome!");
        }
    }

    // Regra: tipo usado por algum imovel nao pode ser renomeado nem excluido
    private boolean tipoEmUso(TipodeImovel tipo) {
        Long empresaDoTipo = tipo.getEmpresa() == null ? null : tipo.getEmpresa().getId();

        if (empresaDoTipo == null) {
            return imovelRepository.existsByTipoIgnoreCaseAndEmpresaIsNull(tipo.getNome());
        }
        return imovelRepository.existsByTipoIgnoreCaseAndEmpresa_Id(tipo.getNome(), empresaDoTipo);
    }

    public List<TipoImovelResponse> listarTodos() {

        var usuarioLogado = getUsuarioLogado();

        if (usuarioLogado.empresa_id() == null) {
            return tipoImovelRepository.findAll()
                    .stream()
                    .map(TipoImovelResponse::new)
                    .toList();
        }

        return tipoImovelRepository.getTiposByEmpresa_Id(usuarioLogado.empresa_id())
                .stream()
                .map(TipoImovelResponse::new)
                .toList();
    }

    public TipoImovelResponse buscarPorId(Long id) {

        var tipo = buscarNaEmpresa(id).orElse(null);
        if(tipo == null){
            return null;
        }

        return new TipoImovelResponse(tipo);
    }

    public TipoImovelResponse criar(CriarTipoImovelRequest request) {

        TipodeImovel tipoBanco = new TipodeImovel(request);
        validarNomeUnico(tipoBanco.getNome(), null);
        tipoBanco.setEmpresa(buscarEmpresaDoUsuarioLogado());
        tipoImovelRepository.save(tipoBanco);

        return new TipoImovelResponse(tipoBanco);
    }

    public TipoImovelResponse atualizar(Long id, CriarTipoImovelRequest request) {

        TipodeImovel tipoBanco = buscarNaEmpresa(id).orElse(null);
        if(tipoBanco != null){

            boolean mudouNome = request.nome() == null
                    || !tipoBanco.getNome().equalsIgnoreCase(request.nome().trim());

            if (mudouNome && tipoEmUso(tipoBanco)) {
                throw new IllegalArgumentException("Tipo de imovel em uso por imoveis nao pode ser renomeado!");
            }

            validarNomeUnico(request.nome(), id);
            tipoBanco.atualizarDados(request.nome(), request.descricao());
            tipoImovelRepository.save(tipoBanco);

            return new TipoImovelResponse(tipoBanco);
        }

        return null;
    }

    public boolean excluir(Long id) {

        TipodeImovel tipoBanco = buscarNaEmpresa(id).orElse(null);
        if(tipoBanco != null){

            if (tipoEmUso(tipoBanco)) {
                throw new IllegalArgumentException("Tipo de imovel em uso por imoveis nao pode ser excluido!");
            }

            tipoImovelRepository.delete(tipoBanco);
            return true;
        }
        return false;
    }
}
