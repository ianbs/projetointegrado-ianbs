package com.ian.projetointegradoianbs.resources;

import java.net.URI;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.services.ColaboradorServices;
import com.ian.projetointegradoianbs.services.EnderecoServices;
import com.ian.projetointegradoianbs.services.UsuarioServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Colaborador>> findAll() {
        return ResponseEntity.ok(colaboradorServices.findAllColaboradores());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Colaborador> findColaborador(@PathVariable Long id) {
        Colaborador objPessoa = colaboradorServices.findColaborador(id);
        return ResponseEntity.ok().body(objPessoa);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Colaborador> insertColaborador(@RequestBody Colaborador colaborador) {
        Usuario usuario = usuarioServices.insertUsuario(colaborador.getUsuario());
        usuario.setColaborador(colaborador);
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));

        List<Endereco> enderecos = enderecoServices.insertAllEnderecos(colaborador.getEnderecos());
        colaborador.setEnderecos(enderecos);

        Colaborador objColaborador = colaboradorServices.insertColaborador(colaborador);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objColaborador.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateColaborador(@RequestBody Colaborador colaborador, @PathVariable Long id) {
        // Seta o Id para o colaborador
        colaborador.setId(id);
        Colaborador colaboradorToUpdate = colaboradorServices.updateColaborador(colaborador); // Salva o colaborador

        List<Endereco> enderecos = enderecoServices.updateAllEnderecos(colaborador.getEnderecos()); // Salva todos os
                                                                                                    // enderecos
        colaboradorToUpdate.setEnderecos(enderecos);

        Usuario usuario = usuarioServices.updateUsuario(colaborador.getUsuario()); // Salva o usuario
        colaboradorToUpdate.setUsuario(usuario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(colaborador.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteColaborador(@PathVariable Long id) {
        Colaborador colaborador = colaboradorServices.findColaborador(id);

        enderecoServices.deleteAllEnderecos(colaborador.getEnderecos());
        colaboradorServices.deleteColaborador(colaborador);
        usuarioServices.deleteUsuario(colaborador.getUsuario().getId());

        return ResponseEntity.noContent().build();
    }
}
