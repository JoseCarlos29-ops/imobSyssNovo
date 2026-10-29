package org.example.imobsyss2.domain.repository;

import org.example.imobsyss2.domain.entities.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findByEmpresaId(Long empresaId);
    Optional<Contrato> findByIdAndEmpresaId(Long id, Long empresaId);

}
