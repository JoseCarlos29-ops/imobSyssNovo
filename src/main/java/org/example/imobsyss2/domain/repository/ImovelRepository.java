package org.example.imobsyss2.domain.repository;

import org.example.imobsyss2.domain.entities.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImovelRepository extends JpaRepository<Imovel, Long> {

    List<Imovel> getImoveisByEmpresa_Id(Long empresa);
}