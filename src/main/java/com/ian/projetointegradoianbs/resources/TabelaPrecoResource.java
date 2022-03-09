package com.ian.projetointegradoianbs.resources;

import java.util.List;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Convenio;
import com.ian.projetointegradoianbs.domain.Procedimento;
import com.ian.projetointegradoianbs.domain.TabelaPreco;
import com.ian.projetointegradoianbs.services.ConvenioServices;
import com.ian.projetointegradoianbs.services.ProcedimentoServices;
import com.ian.projetointegradoianbs.services.TabelaPrecosServices;

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

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/api/convenios/tabela")
public class TabelaPrecoResource {

    @Autowired
    TabelaPrecosServices tabelaPrecosServices;

    @Autowired
    private ConvenioServices convenioServices;

    @Autowired
    private ProcedimentoServices procedimentoServices;

    @GetMapping
    public ResponseEntity<List<TabelaPreco>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(tabelaPrecosServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TabelaPreco> findByData(@PathVariable Integer id) {
        return ResponseEntity.ok().body(tabelaPrecosServices.findById(id));
    }

    @GetMapping("/convenio/{id}")
    public ResponseEntity<List<TabelaPreco>> findByConvenio(@PathVariable UUID id) {
        Convenio convenio = convenioServices.findById(id);
        return ResponseEntity.ok().body(tabelaPrecosServices.findByConvenio(convenio));
    }

    @PostMapping
    public ResponseEntity<TabelaPreco> save(@RequestBody TabelaPreco tabelaPreco) {
        Procedimento procedimento = procedimentoServices.findById(tabelaPreco.getProcedimento().getId());
        tabelaPreco.setProcedimento(procedimento);
        Convenio convenio = convenioServices.findById(tabelaPreco.getConvenio().getId());
        tabelaPreco.setConvenio(convenio);
        return ResponseEntity.status(HttpStatus.CREATED).body(tabelaPrecosServices.save(tabelaPreco));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TabelaPreco> save(@RequestBody TabelaPreco tabelaPreco, @PathVariable Integer id) {
        tabelaPreco.setId(id);
        Procedimento procedimento = procedimentoServices.findById(tabelaPreco.getProcedimento().getId());
        tabelaPreco.setProcedimento(procedimento);
        Convenio convenio = convenioServices.findById(tabelaPreco.getConvenio().getId());
        tabelaPreco.setConvenio(convenio);
        return ResponseEntity.status(HttpStatus.OK).body(tabelaPrecosServices.update(tabelaPreco));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        TabelaPreco tabelaPreco = tabelaPrecosServices.findById(id);
        tabelaPrecosServices.delete(tabelaPreco.getId());
        return ResponseEntity.noContent().build();
    }
}
