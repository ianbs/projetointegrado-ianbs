package com.ian.projetointegradoianbs.resources;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.domain.Paciente;
import com.ian.projetointegradoianbs.services.EnderecoServices;
import com.ian.projetointegradoianbs.services.PacienteServices;

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
@RequestMapping(value = "/api/pacientes")
public class PacienteResource {

    @Autowired
    PacienteServices pacienteServices;

    @Autowired
    private EnderecoServices enderecoServices;

    @GetMapping
    public ResponseEntity<List<Paciente>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServices.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(pacienteServices.findById(id));
    }

    @PostMapping
    public ResponseEntity<Paciente> save(@RequestBody Paciente paciente) {
        List<Endereco> enderecos = enderecoServices.save(paciente.getEnderecos());
        paciente.setEnderecos(enderecos);
        paciente.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteServices.save(paciente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> updatePaciente(@RequestBody Paciente paciente, @PathVariable UUID id) {
        paciente.setId(id);
        List<Endereco> enderecos = enderecoServices.update(paciente.getEnderecos());
        paciente.setEnderecos(enderecos);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteServices.update(paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable UUID id) {
        Paciente paciente = pacienteServices.findById(id);

        enderecoServices.delete(paciente.getEnderecos());
        pacienteServices.delete(paciente);

        return ResponseEntity.noContent().build();
    }
}
