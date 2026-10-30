/*package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.CriarTipoImovelRequest;
import org.example.imobsyss2.application.dtos.TipoImovelResponse;
import org.example.imobsyss2.domain.entities.TipodeImovel;
import org.example.imobsyss2.domain.exceptions.RegraNegocioException;
import org.example.imobsyss2.domain.repository.ImovelRepository;
import org.example.imobsyss2.domain.repository.TipoImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UsuarioLogadoProvider usuarioLogadoProvider;

    // Usuario sem empresa (admin geral) enxerga tudo; os demais so a propria empresa
    private Optional<TipodeImovel> buscarNaEmpresa(Long id) {
        Long empresaId = usuarioLogadoProvider.empresaId();

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
        Long empresaId = usuarioLogadoProvider.empresaId();

        boolean duplicado = tipoImovelRepository.findByNomeIgnoreCase(nome.trim())
                .stream()
                .filter(t -> idAtual == null || !idAtual.equals(t.getId()))
                .anyMatch(t -> Objects.equals(t.getEmpresa() == null ? null : t.getEmpresa().getId(), empresaId));

        if (duplicado) {
            throw new RegraNegocioException("Ja existe um tipo de imovel com este nome!");
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
        Long empresaId = usuarioLogadoProvider.empresaId();

        if (empresaId == null) {
            return tipoImovelRepository.findAll()
                    .stream()
                    .map(TipoImovelResponse::new)
                    .toList();
        }

        return tipoImovelRepository.getTiposByEmpresa_Id(empresaId)
                .stream()
                .map(TipoImovelResponse::new)
                .toList();
    }

    public TipoImovelResponse buscarPorId(Long id) {
        return buscarNaEmpresa(id)
                .map(TipoImovelResponse::new)
                .orElse(null);
    }

    public TipoImovelResponse criar(CriarTipoImovelRequest request) {
        validarNomeUnico(request.nome(), null);

        TipodeImovel tipo = new TipodeImovel();
        tipo.atualizarDados(request.nome(), request.descricao());
        tipo.setEmpresa(usuarioLogadoProvider.empresa());

        return new TipoImovelResponse(tipoImovelRepository.save(tipo));
    }

    public TipoImovelResponse atualizar(Long id, CriarTipoImovelRequest request) {
        TipodeImovel tipoBanco = buscarNaEmpresa(id).orElse(null);

        if (tipoBanco == null) {
            return null;
        }

        boolean mudouNome = request.nome() == null
                || !tipoBanco.getNome().equalsIgnoreCase(request.nome().trim());

        if (mudouNome && tipoEmUso(tipoBanco)) {
            throw new RegraNegocioException("Tipo de imovel em uso por imoveis nao pode ser renomeado!");
        }

        validarNomeUnico(request.nome(), id);
        tipoBanco.atualizarDados(request.nome(), request.descricao());

        return new TipoImovelResponse(tipoImovelRepository.save(tipoBanco));
    }

    public boolean excluir(Long id) {
        TipodeImovel tipoBanco = buscarNaEmpresa(id).orElse(null);

        if (tipoBanco == null) {
            return false;
        }

        if (tipoEmUso(tipoBanco)) {
            throw new RegraNegocioException("Tipo de imovel em uso por imoveis nao pode ser excluido!");
        }

        tipoImovelRepository.delete(tipoBanco);
        return true;
    }
}

 */