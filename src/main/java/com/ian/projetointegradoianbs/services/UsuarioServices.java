package com.ian.projetointegradoianbs.services;

import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UsuarioServices implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado.");
        }
        return usuarioOptional.get();
    }

}
