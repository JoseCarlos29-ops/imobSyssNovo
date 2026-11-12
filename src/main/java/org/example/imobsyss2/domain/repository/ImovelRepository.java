package org.example.imobsyss2.domain.repository;

import org.example.imobsyss2.domain.entities.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    List<Imovel> getImoveisByEmpresa_Id(Long empresa);

    Optional<Imovel> findByIdAndEmpresa_Id(Long id, Long empresaId);

    // usados na regra "tipo de imovel em uso nao pode ser renomeado nem excluido"
    boolean existsByTipoIgnoreCaseAndEmpresa_Id(String tipo, Long empresaId);

    boolean existsByTipoIgnoreCaseAndEmpresaIsNull(String tipo);
}
