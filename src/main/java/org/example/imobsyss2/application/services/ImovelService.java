package org.example.imobsyss2.application.services;

import org.example.imobsyss2.application.dtos.AtualizarStatusImovel;

import org.example.imobsyss2.application.dtos.EnumStatusImovel;
import org.example.imobsyss2.domain.entities.Imovel;
import org.example.imobsyss2.domain.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public List<Imovel> listarTodos() {

        return imovelRepository.findAll();
    }

    public Imovel buscarPorId(Long id) {

        return imovelRepository.findById(id).orElse(null);
    }

    public Imovel criar(Imovel imovel) {

        return imovelRepository.save(imovel);
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

    public Imovel atualizar(Long id, Imovel imovel) {

        Imovel imovelBanco = imovelRepository.findById(id).orElse(null);

        if (imovelBanco == null) {
            return null;
        }

        imovelBanco.setStatus(imovel.getStatus());
        imovelBanco.setEndereco(imovel.getEndereco());
        imovelBanco.setTipo(imovel.getTipo());
        imovelBanco.setValor(imovel.getValor());
        imovelBanco.setArea(imovel.getArea());
        imovelBanco.setQuartos(imovel.getQuartos());
        imovelBanco.setBanheiros(imovel.getBanheiros());
        imovelBanco.setDescricao(imovel.getDescricao());
        imovelBanco.setProprietario(imovel.getProprietario());

        return imovelRepository.save(imovelBanco);
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