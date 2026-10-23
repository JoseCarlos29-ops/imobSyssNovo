package org.example.imobsyss2.domain.repository;

import org.example.imobsyss2.domain.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {


    Optional<Token> findTokenByToken(String token);
}