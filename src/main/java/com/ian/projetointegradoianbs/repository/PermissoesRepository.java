package com.ian.projetointegradoianbs.repository;

import com.ian.projetointegradoianbs.domain.Permissoes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissoesRepository extends JpaRepository<Permissoes, Integer> {
    Permissoes findByItem(String item);
}
