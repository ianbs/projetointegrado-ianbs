package com.ian.projetointegradoianbs.resources;

import java.net.URI;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Procedimento;
import com.ian.projetointegradoianbs.services.ProcedimentoServices;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/procedimentos")
public class ProcedimentoResource {
    @Autowired
    ProcedimentoServices procedimentoServices;

    @GetMapping
    public ResponseEntity<List<Procedimento>> findAll() {
        return ResponseEntity.ok().body(procedimentoServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Procedimento> findById(@PathVariable Long id) {
        Procedimento procedimento = procedimentoServices.findById(id);
        return ResponseEntity.ok().body(procedimento);
    }

    @GetMapping("/tuss")
    public ResponseEntity<Procedimento> findByCodigoTuss(@RequestBody FindCodigoTuss findCodigoTuss) {
        Procedimento procedimento = procedimentoServices.findByCodigoTuss(findCodigoTuss.getCodigo());
        return ResponseEntity.ok().body(procedimento);
    }

    @PostMapping
    public ResponseEntity<Procedimento> save(@RequestBody Procedimento procedimento) {
        Procedimento objProcedimento = procedimentoServices.save(procedimento);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objProcedimento.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Procedimento> update(@RequestBody Procedimento procedimento,
            @PathVariable Long id) {
        procedimento.setId(id);
        procedimentoServices.update(procedimento);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Procedimento> delete(@PathVariable Long id) {
        Procedimento procedimento = procedimentoServices.findById(id);
        procedimentoServices.delete(procedimento.getId());
        return ResponseEntity.noContent().build();
    }
}

@Data
class FindCodigoTuss {
    private String codigo;
}