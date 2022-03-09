package com.ian.projetointegradoianbs.resources;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Convenio;
import com.ian.projetointegradoianbs.services.ConvenioServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/convenios")
public class ConvenioResource {

    @Autowired
    ConvenioServices convenioServices;

    @GetMapping
    public ResponseEntity<List<Convenio>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(convenioServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Convenio> findConvenio(@PathVariable UUID id) {
        Convenio convenio = convenioServices.findById(id);
        return ResponseEntity.ok().body(convenio);
    }

    @GetMapping("/nome")
    public ResponseEntity<Convenio> findByName(@RequestBody FindNome findNome) {
        Convenio convenio = convenioServices.findByName(findNome.getNome());
        return ResponseEntity.ok().body(convenio);
    }

    @PostMapping
    public ResponseEntity<Convenio> save(@RequestBody Convenio convenio) {
        convenio.setDataVinculo(LocalDate.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.OK).body(convenioServices.save(convenio));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Convenio> update(@RequestBody Convenio convenio, @PathVariable UUID id) {
        convenio.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(convenioServices.update(convenio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Convenio> deleteConvenio(@PathVariable UUID id) {
        Convenio convenio = convenioServices.findById(id);
        convenioServices.delete(convenio.getId());
        return ResponseEntity.noContent().build();
    }

}

@Data
class FindNome {
    private String nome;
}