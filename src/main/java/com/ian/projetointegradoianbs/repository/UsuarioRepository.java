package com.ian.projetointegradoianbs.repository;

import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Optional<Usuario> findByUsername(String nome);
}
