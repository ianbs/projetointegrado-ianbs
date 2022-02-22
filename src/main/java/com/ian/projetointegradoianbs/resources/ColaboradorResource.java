package com.ian.projetointegradoianbs.resources;

import java.net.URI;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.services.ColaboradorServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Colaborador> findColaborador(@PathVariable Long id) {
        Colaborador objPessoa = colaboradorServices.findColaborador(id);
        return ResponseEntity.ok().body(objPessoa);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Colaborador>> findAll() {
        return ResponseEntity.ok(colaboradorServices.findAllColaboradores());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Colaborador> insertColaborador(@RequestBody Colaborador Colaborador) {
        Colaborador objColaborador = colaboradorServices.insertColaborador(Colaborador);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objColaborador.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateColaborador(@RequestBody Colaborador colaborador, @PathVariable Long id) {
        colaborador.setId(id);
        colaborador = colaboradorServices.updateColaborador(colaborador);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteColaborador(@PathVariable Long id) {
        colaboradorServices.deleteColaborador(id);
        return ResponseEntity.noContent().build();
    }
}
