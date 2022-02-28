package com.ian.projetointegradoianbs.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Permissoes;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.repository.PermissoesRepository;
import com.ian.projetointegradoianbs.repository.UsuarioRepository;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Usuario find(Long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Usuario.class.getName()));
    }

    public List<Usuario> listAll() {
        return usuarioRepository.findAll();
    }

    public Usuario insertUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Usuario usuario) {
        find(usuario.getId());
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public void deleteUsuario(Long id) {
        Usuario usuario = find(id);
        usuarioRepository.delete(usuario);
    }

    public Permissoes insertPermissoes(Permissoes permissoes) {
        return permissoesRepository.save(permissoes);
    }

    public void addPermissoesUsuario(String username, String item) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(username);
        Permissoes permissoes = permissoesRepository.findByItem(item);
        usuario.get().getPermissoes().add(permissoes);
    }

}
