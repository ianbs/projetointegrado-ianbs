package com.ian.projetointegradoianbs.repository;

import java.util.Optional;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Profissional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, UUID> {
    public Optional<Profissional> findByUsuario(UUID usuarioId);
}
