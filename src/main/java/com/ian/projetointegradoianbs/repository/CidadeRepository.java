package com.ian.projetointegradoianbs.repository;

import com.ian.projetointegradoianbs.domain.Cidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
    Cidade findByNome(String nome);
}
