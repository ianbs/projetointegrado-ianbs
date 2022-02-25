package com.ian.projetointegradoianbs.resources;

import java.net.URI;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Profissional;
import com.ian.projetointegradoianbs.domain.Usuario;
import com.ian.projetointegradoianbs.services.ProfissionalServices;
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
@RequestMapping(value = "/api/profissionais")
public class ProfissionalResource {

    @Autowired
    ProfissionalServices profissionalServices;

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private PasswordEncoder usuarioEncoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Profissional>> findAll() {
        return ResponseEntity.ok(profissionalServices.findAllProfissionais());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Profissional> findProfissional(@PathVariable Long id) {
        Profissional objPessoa = profissionalServices.findProfissional(id);
        return ResponseEntity.ok().body(objPessoa);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Profissional> insertProfissional(@RequestBody Profissional profissional) {
        Usuario usuario = usuarioServices.insertUsuario(profissional.getUsuario());
        usuario.setProfissional(profissional);
        usuario.setPassword(usuarioEncoder.encode(usuario.getPassword()));
        Profissional objProfissional = profissionalServices.insertProfissional(profissional);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objProfissional.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateProfissional(@RequestBody Profissional profissional, @PathVariable Long id) {
        profissional.setId(id);
        profissional = profissionalServices.updateProfissional(profissional);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteProfissional(@PathVariable Long id) {
        profissionalServices.deleteProfissional(id);
        return ResponseEntity.noContent().build();
    }

    // @RequestMapping(method = RequestMethod.GET)
    // public ResponseEntity<List<PessoaDTO>> findAllPessoas() {
    // List<Profissional> objPessoa = service.findAllPessoa();
    // List<PessoaDTO> pessoaDTOs = objPessoa.stream().map((obj) -> new
    // PessoaDTO(obj)).collect(Collectors.toList());
    // return ResponseEntity.ok().body(pessoaDTOs);
    // }

}
