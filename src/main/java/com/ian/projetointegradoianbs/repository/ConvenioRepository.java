package com.ian.projetointegradoianbs.repository;

import java.util.Optional;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Convenio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, UUID> {
    Optional<Convenio> findByNome(String nome);
}
