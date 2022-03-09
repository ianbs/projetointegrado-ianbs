package com.ian.projetointegradoianbs.repository;

import java.util.Optional;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    public Optional<Usuario> findByUsername(String nome);
}
