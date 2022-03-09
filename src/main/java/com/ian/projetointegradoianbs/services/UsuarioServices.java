package com.ian.projetointegradoianbs.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import com.ian.projetointegradoianbs.domain.Permissoes;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.repository.PermissoesRepository;
import com.ian.projetointegradoianbs.repository.UsuarioRepository;
import com.ian.projetointegradoianbs.services.exceptions.DataIntegrityException;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioServices implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder usuarioEncoder;

    @Autowired
    private PermissoesRepository permissoesRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);
        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado.");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        usuarioOptional.get().getPermissoes().forEach(permissao -> {
            authorities.add(new SimpleGrantedAuthority(permissao.getItem()));
        });
        return usuarioOptional.get();
    }

    public Usuario findById(UUID id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Usuario.class.getName()));
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        findById(usuario.getId());
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public void delete(UUID id) {
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }

    public Permissoes savePermissoes(Permissoes permissoes) {
        return permissoesRepository.save(permissoes);
    }

    public void savePermissoesByUsuario(String username, String item) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        Permissoes permissoes = permissoesRepository.findByItem(item);
        usuario.get().getPermissoes().add(permissoes);
        usuarioRepository.save(usuario.get());
    }

}
