/*package org.example.imobsyss2.domain.repository;

import org.example.imobsyss2.domain.entities.TipodeImovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoImovelRepository extends JpaRepository<TipodeImovel, Long> {

    List<TipodeImovel> getTiposByEmpresa_Id(Long empresaId);

    Optional<TipodeImovel> findByIdAndEmpresa_Id(Long id, Long empresaId);

    // usado na regra "nome unico por empresa"
    List<TipodeImovel> findByNomeIgnoreCase(String nome);
}
*/
