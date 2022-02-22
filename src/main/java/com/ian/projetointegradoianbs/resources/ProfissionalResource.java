package com.ian.projetointegradoianbs.resources;

import java.net.URI;

import com.ian.projetointegradoianbs.domain.Profissional;
import com.ian.projetointegradoianbs.services.ProfissionalServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Profissional> findProfissional(@PathVariable Long id) {
        Profissional objPessoa = profissionalServices.findProfissional(id);
        return ResponseEntity.ok().body(objPessoa);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Profissional> insertProfissional(@RequestBody Profissional profissional) {
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
