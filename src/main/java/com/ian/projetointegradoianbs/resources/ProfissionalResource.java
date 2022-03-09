package com.ian.projetointegradoianbs.resources;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.domain.Profissional;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.services.EnderecoServices;
import com.ian.projetointegradoianbs.services.ProfissionalServices;
import com.ian.projetointegradoianbs.services.UsuarioServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/profissionais")
public class ProfissionalResource {

    @Autowired
    ProfissionalServices profissionalServices;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private EnderecoServices enderecoServices;

    @Autowired
    private PasswordEncoder usuarioEncoder;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid Profissional profissional) {
        Usuario usuario = usuarioServices.save(profissional.getUsuario());
        usuario.setProfissional(profissional);
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        List<Endereco> enderecos = enderecoServices.save(profissional.getEnderecos());
        profissional.setEnderecos(enderecos);
        profissional.setDataVinculo(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalServices.save(profissional));
    }

    @GetMapping
    public ResponseEntity<List<Profissional>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(profissionalServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(profissionalServices.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profissional> update(@RequestBody Profissional profissional, @PathVariable UUID id) {
        profissional.setId(id);
        List<Endereco> enderecos = enderecoServices.update(profissional.getEnderecos());
        profissional.setEnderecos(enderecos);
        Usuario usuario = usuarioServices.update(profissional.getUsuario()); // Salva o usuario
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        usuario.setProfissional(profissional);
        profissional.setUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(profissionalServices.update(profissional));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        enderecoServices.delete(profissionalServices.findById(id).getEnderecos());
        profissionalServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
