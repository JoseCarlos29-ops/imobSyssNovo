package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusImovel;
import org.example.imobsyss2.application.dtos.EnumStatusImovel;
import org.example.imobsyss2.application.dtos.ImovelRequest;
import org.example.imobsyss2.application.dtos.ImovelResponse;
import org.example.imobsyss2.domain.entities.Imovel;
import org.example.imobsyss2.domain.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public List<ImovelResponse> listarTodos() {
        return imovelRepository.findAll().stream()
                .map(ImovelResponse::new)
                .toList();
    }

    public ImovelResponse buscarPorId(Long id) {
        return imovelRepository.findById(id)
                .map(ImovelResponse::new)
                .orElse(null);
    }

    public ImovelResponse criar(ImovelRequest request) {
        Imovel imovel = new Imovel();
        imovel.setEndereco(request.endereco());
        imovel.setTipo(request.tipo());
        imovel.setValor(request.valor());
        imovel.setArea(request.area());
        imovel.setQuartos(request.quartos());
        imovel.setBanheiros(request.banheiros());
        imovel.setDescricao(request.descricao());
        imovel.setProprietario(request.proprietario());
        imovel.setStatus(EnumStatusImovel.DISPONIVEL); // use um valor que exista no seu enum

        return new ImovelResponse(imovelRepository.save(imovel));
    }

    public boolean atualizarStatus(Long id, AtualizarStatusImovel request) {
        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);

        if (imovelBanco == null) {
            return false;
        }

        imovelBanco.setStatus(request.statusImovel());
        imovelRepository.save(imovelBanco);
        return true;
    }

    public ImovelResponse atualizar(Long id, ImovelRequest request) {
        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);

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
        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);

        if (imovelBanco == null) {
            return false;
        }

        imovelBanco.setStatus(EnumStatusImovel.INDISPONIVEL);
        imovelRepository.save(imovelBanco);
        return true;
    }
}