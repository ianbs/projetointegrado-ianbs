package com.ian.projetointegradoianbs.repository;

import com.ian.projetointegradoianbs.domain.Colaborador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

}
