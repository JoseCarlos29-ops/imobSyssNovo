package org.example.imobsyss2.domain.repository;

import org.example.imobsyss2.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> getClientesByEmpresa_Id(Long empresa);

    Optional<Cliente> findByIdAndEmpresa_Id(Long id, Long empresaId);
}
