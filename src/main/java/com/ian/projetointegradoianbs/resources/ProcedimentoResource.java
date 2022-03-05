package com.ian.projetointegradoianbs.resources;

import java.net.URI;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Procedimento;
import com.ian.projetointegradoianbs.services.ProcedimentoServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.Data;

@RestController
@RequestMapping(value = "/api/procedimentos")
public class ProcedimentoResource {
    @Autowired
    ProcedimentoServices procedimentoServices;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Procedimento>> findAll() {
        return ResponseEntity.ok().body(procedimentoServices.findAllProcedimentos());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Procedimento> findProcedimento(@PathVariable Long id) {
        Procedimento procedimento = procedimentoServices.findProcedimento(id);
        return ResponseEntity.ok().body(procedimento);
    }

    @RequestMapping(value = "/tuss", method = RequestMethod.GET)
    public ResponseEntity<Procedimento> findProcedimentoByCodigoTuss(@RequestBody FindCodigoTuss findCodigoTuss) {
        Procedimento procedimento = procedimentoServices.findProcedimentoByCodigoTuss(findCodigoTuss.getCodigo());
        return ResponseEntity.ok().body(procedimento);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Procedimento> insertProcedimento(@RequestBody Procedimento procedimento) {
        Procedimento objProcedimento = procedimentoServices.insertProcedimento(procedimento);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objProcedimento.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Procedimento> updateProcedimento(@RequestBody Procedimento procedimento,
            @PathVariable Long id) {
        procedimento.setId(id);
        procedimentoServices.updateProcedimento(procedimento);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Procedimento> deleteProcedimento(@PathVariable Long id) {
        Procedimento procedimento = procedimentoServices.findProcedimento(id);
        procedimentoServices.deleteProcedimento(procedimento.getId());
        return ResponseEntity.noContent().build();
    }
}

@Data
class FindCodigoTuss {
    private String codigo;
}