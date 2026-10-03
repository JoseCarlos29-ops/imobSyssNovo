package org.example.imobsyss2.domain.repository;

import org.example.imobsyss2.domain.entities.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {
}
