package org.example.imobsyss2.application.services;

import lombok.RequiredArgsConstructor;
import org.example.imobsyss2.application.dtos.CriarTipoImovelRequest;
import org.example.imobsyss2.domain.entities.TipodeImovel;
import org.example.imobsyss2.domain.repository.TipoImovelRepository;
import org.springframework.stereotype.Service;

public class TipodeImovelService {

    @Service
    @RequiredArgsConstructor
    public class TipoImovelService {

        private final TipoImovelRepository tipoImovelRepository;

        public TipodeImovel criar(CriarTipoImovelRequest request) {
            TipodeImovel tipo = new TipodeImovel();
            tipoImovel.setNome(request.getNome());
            tipoImovel.setDescricao(request.getDescricao());
            return tipoImovelRepository.save(tipoImovel);
        }

        public List<TipoImovel> listar() {
            return tipoImovelRepository.findAll();
        }

        public TipoImovel buscarPorId(Long id) {
            return tipoImovelRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tipo de imóvel não encontrado"));
        }

        public TipoImovel atualizar(Long id, CriarTipoImovelRequest request) {
            TipoImovel tipo = buscarPorId(id);
            tipo.setNome(request.getNome());
            tipo.setDescricao(request.getDescricao());
            return tipoImovelRepository.save(tipo);
        }

        public void deletar(Long id) {
            tipoImovelRepository.deleteById(id);
        }
    }
}
