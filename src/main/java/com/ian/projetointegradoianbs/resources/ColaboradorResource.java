package com.ian.projetointegradoianbs.resources;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.services.ColaboradorServices;
import com.ian.projetointegradoianbs.services.EnderecoServices;
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
@RequestMapping(value = "/api/colaborador")
public class ColaboradorResource {
    @Autowired
    ColaboradorServices colaboradorServices;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private EnderecoServices enderecoServices;

    @Autowired
    private PasswordEncoder usuarioEncoder;

    @PostMapping
    public ResponseEntity<Colaborador> save(@RequestBody Colaborador colaborador) {
        Usuario usuario = usuarioServices.save(colaborador.getUsuario());
        usuario.setColaborador(colaborador);
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        List<Endereco> enderecos = enderecoServices.save(colaborador.getEnderecos());
        colaborador.setEnderecos(enderecos);
        colaborador.setDataVinculo(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(colaboradorServices.save(colaborador));
    }

    @GetMapping
    public ResponseEntity<List<Colaborador>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(colaboradorServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colaborador> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(colaboradorServices.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colaborador> update(@RequestBody Colaborador colaborador, @PathVariable UUID id) {
        colaborador.setId(id);
        List<Endereco> enderecos = enderecoServices.update(colaborador.getEnderecos());
        colaborador.setEnderecos(enderecos);
        Usuario usuario = usuarioServices.update(colaborador.getUsuario()); // Salva o usuario
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        usuario.setColaborador(colaborador);
        colaborador.setUsuario(usuario);
        return ResponseEntity.status(HttpStatus.OK).body(colaboradorServices.update(colaborador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        enderecoServices.delete(colaboradorServices.findById(id).getEnderecos());
        colaboradorServices.delete(id);
        return ResponseEntity.noContent().build();
    }
}
