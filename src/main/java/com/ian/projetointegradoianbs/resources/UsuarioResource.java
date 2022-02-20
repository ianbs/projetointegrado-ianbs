package com.ian.projetointegradoianbs.resources;

import java.util.List;

import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder usuarioEncoder;

    @GetMapping("/all")
    public ResponseEntity<List<Usuario>> listAll() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }
}
