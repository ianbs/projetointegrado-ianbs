package com.ian.projetointegradoianbs.resources;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Agenda;
import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.domain.Paciente;
import com.ian.projetointegradoianbs.domain.Profissional;
import com.ian.projetointegradoianbs.services.AgendaServices;
import com.ian.projetointegradoianbs.services.ColaboradorServices;
import com.ian.projetointegradoianbs.services.PacienteServices;
import com.ian.projetointegradoianbs.services.ProfissionalServices;

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
@RequestMapping(value = "/api/agenda")
public class AgendaResource {

    @Autowired
    AgendaServices agendaServices;

    @Autowired
    private ProfissionalServices profissionalServices;

    @Autowired
    private ColaboradorServices colaboradorServices;

    @Autowired
    private PacienteServices pacienteServices;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Agenda>> findAllAgenda() {
        return ResponseEntity.ok().body(agendaServices.findAllAgenda());
    }

    @RequestMapping(value = "/descricao", method = RequestMethod.GET)
    public ResponseEntity<Agenda> findAgendaByDescricao(@RequestBody FindDescricao findDescricao) {
        Agenda agenda = agendaServices.findAgendaByDescricao(findDescricao.getDescricao());
        return ResponseEntity.ok().body(agenda);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Agenda> insertAgenda(@RequestBody Agenda agenda) {
        Agenda objAgenda = agendaServices.insertAgenda(agenda);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objAgenda.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Agenda> updateAgenda(@RequestBody Agenda agenda, @PathVariable Integer id) {
        Agenda agendaToUpdate = agendaServices.updateAgenda(agenda);

        Profissional profissional = profissionalServices.findProfissional(agenda.getProfissional().getId());
        agendaToUpdate.setProfissional(profissional);

        Colaborador colaborador = colaboradorServices.findColaborador(agenda.getColaborador().getId());
        agendaToUpdate.setColaborador(colaborador);

        Paciente paciente = pacienteServices.findPaciente(agenda.getPaciente().getId());
        agendaToUpdate.setPaciente(paciente);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> deleteAgenda(@PathVariable Integer id) {
        Agenda agenda = agendaServices.findAgenda(id);
        agendaServices.deleteAgenda(agenda.getId());
        return ResponseEntity.noContent().build();
    }
}

@Data
class FindDescricao {
    private String descricao;
}

@Data
class FindData {
    private LocalDate data;
}
