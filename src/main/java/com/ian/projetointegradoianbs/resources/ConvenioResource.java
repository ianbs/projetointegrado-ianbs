package com.ian.projetointegradoianbs.resources;

import java.net.URI;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Convenio;
import com.ian.projetointegradoianbs.services.ConvenioServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/api/convenios")
public class ConvenioResource {

    @Autowired
    ConvenioServices convenioServices;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Convenio>> findAll() {
        return ResponseEntity.ok(convenioServices.findAllConvenios());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Convenio> findConvenio(@PathVariable Integer id) {
        Convenio convenio = convenioServices.findConvenio(id);
        return ResponseEntity.ok().body(convenio);
    }

    @RequestMapping(value = "/nome", method = RequestMethod.GET)
    public ResponseEntity<Convenio> findConvenioByName(@RequestBody FindNome findNome) {
        Convenio convenio = convenioServices.findConvenioByName(findNome.getNome());
        return ResponseEntity.ok().body(convenio);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Convenio> insertConvenio(@RequestBody Convenio convenio) {
        Convenio objConvenio = convenioServices.insertConvenio(convenio);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objConvenio.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Convenio> updateConvenio(@RequestBody Convenio convenio, @PathVariable Integer id) {
        convenio.setId(id);
        convenioServices.updateConvenio(convenio);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Convenio> deleteConvenio(@PathVariable Integer id) {
        Convenio convenio = convenioServices.findConvenio(id);
        convenioServices.deleteConvenio(convenio.getId());
        return ResponseEntity.noContent().build();
    }

}

@Data
class FindNome {
    private String nome;
}