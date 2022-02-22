package com.ian.projetointegradoianbs.repository;

import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Profissional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    public Optional<Profissional> findByUsuario(Integer usuarioId);
}
