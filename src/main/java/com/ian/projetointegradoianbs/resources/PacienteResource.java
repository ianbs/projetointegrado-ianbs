package com.ian.projetointegradoianbs.resources;

import java.net.URI;
import java.util.List;

import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.domain.Paciente;
import com.ian.projetointegradoianbs.services.EnderecoServices;
import com.ian.projetointegradoianbs.services.PacienteServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/api/pacientes")
public class PacienteResource {

    @Autowired
    PacienteServices pacienteServices;

    @Autowired
    private EnderecoServices enderecoServices;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Paciente>> findAll() {
        return ResponseEntity.ok(pacienteServices.findAllPacientes());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Paciente> findPaciente(@PathVariable Long id) {
        Paciente objPessoa = pacienteServices.findPaciente(id);
        return ResponseEntity.ok().body(objPessoa);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Paciente> insertPaciente(@RequestBody Paciente paciente) {
        List<Endereco> enderecos = enderecoServices.insertAllEnderecos(paciente.getEnderecos());
        paciente.setEnderecos(enderecos);

        Paciente objPaciente = pacienteServices.insertPaciente(paciente);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objPaciente.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePaciente(@RequestBody Paciente paciente, @PathVariable Long id) {
        paciente.setId(id);
        Paciente pacienteToUpdate = pacienteServices.updatePaciente(paciente);

        List<Endereco> enderecos = enderecoServices.updateAllEnderecos(paciente.getEnderecos());
        pacienteToUpdate.setEnderecos(enderecos);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePaciente(@PathVariable Long id) {
        Paciente paciente = pacienteServices.findPaciente(id);

        enderecoServices.deleteAllEnderecos(paciente.getEnderecos());
        pacienteServices.deletePaciente(paciente);

        return ResponseEntity.noContent().build();
    }
}
