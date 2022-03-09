package com.ian.projetointegradoianbs.repository;

import java.util.Optional;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Colaborador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, UUID> {
    public Optional<Colaborador> findByUsuario(UUID usuarioId);
}
