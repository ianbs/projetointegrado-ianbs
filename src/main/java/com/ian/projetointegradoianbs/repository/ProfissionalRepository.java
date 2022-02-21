package com.ian.projetointegradoianbs.repository;

import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Profissional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    public Optional<Profissional> findByUsuario(Long usuarioId);
}
