package com.ian.projetointegradoianbs.repository;

import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByNome(String nome);
}
