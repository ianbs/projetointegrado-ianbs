package com.ian.projetointegradoianbs.resources;

import java.io.IOException;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Profissional;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.repository.UsuarioRepository;
import com.ian.projetointegradoianbs.services.ProfissionalServices;
import com.ian.projetointegradoianbs.services.UsuarioServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioResource {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProfissionalServices profissionalServices;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private PasswordEncoder usuarioEncoder;

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Profissional profissional = profissionalServices.insertProfissional(usuario.getProfissional());
        profissional.setUsuario(usuario);
        usuario.setProfissional(profissional);
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> find(@PathVariable Long id) {
        Usuario objPessoa = usuarioServices.find(id);
        return ResponseEntity.ok().body(objPessoa);
    }

}
