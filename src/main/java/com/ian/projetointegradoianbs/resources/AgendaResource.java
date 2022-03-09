package com.ian.projetointegradoianbs.resources;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.ian.projetointegradoianbs.domain.Agenda;
import com.ian.projetointegradoianbs.services.AgendaServices;

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
@RequestMapping(value = "/api/agenda")
public class AgendaResource {

    @Autowired
    AgendaServices agendaServices;

    @GetMapping
    public ResponseEntity<List<Agenda>> findAllAgenda() {
        return ResponseEntity.status(HttpStatus.OK).body(agendaServices.findAll());
    }

    @GetMapping("/descricao")
    public ResponseEntity<Agenda> findByDescricao(@RequestBody FindDescricao findDescricao) {
        Agenda agenda = agendaServices.findByDescricao(findDescricao.getDescricao());
        return ResponseEntity.ok().body(agenda);
    }

    @GetMapping("/data")
    public ResponseEntity<List<Agenda>> findByData(@RequestBody FindData findData) {
        List<Agenda> agenda = agendaServices.findByData(findData.getData());
        return ResponseEntity.ok().body(agenda);
    }

    @GetMapping("/profissional/{id}")
    public ResponseEntity<List<Agenda>> findByProfissional(@PathVariable UUID id) {
        List<Agenda> agenda = agendaServices.findByProfissional(id);
        return ResponseEntity.ok().body(agenda);
    }

    @PostMapping
    public ResponseEntity<Agenda> save(@RequestBody Agenda agenda) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendaServices.save(agenda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agenda> update(@RequestBody Agenda agenda, @PathVariable UUID id) {
        agenda.setId(id);
        return ResponseEntity.status(HttpStatus.OK).body(agendaServices.update(agenda));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        Agenda agenda = agendaServices.findById(id);
        agendaServices.delete(agenda.getId());
        return ResponseEntity.noContent().build();
    }
}

@Data
class FindDescricao {
    private String descricao;
}

@Data
class FindData {
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate data;
}
