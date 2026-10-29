package org.example.imobsyss2.domain.repository;

import org.example.imobsyss2.domain.entities.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    List<Imovel> findByEmpresaId(Long empresaId);
    Optional<Imovel> findByIdAndEmpresaId(Long id, Long empresaId);

}
