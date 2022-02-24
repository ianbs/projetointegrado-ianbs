package com.ian.projetointegradoianbs.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.domain.Paciente;
import com.ian.projetointegradoianbs.repository.EnderecoRepository;
import com.ian.projetointegradoianbs.repository.PacienteRepository;
import com.ian.projetointegradoianbs.services.exceptions.DataIntegrityException;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class PacienteServices {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public List<Paciente> findAllPacientes() {
        List<Paciente> obj = pacienteRepository.findAll();
        return obj;
    }

    public Paciente findPaciente(Long id) {
        Optional<Paciente> optional = pacienteRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Paciente.class.getName()));
    }

    public Paciente insertPaciente(Paciente paciente) {
        for (Endereco endereco : paciente.getEnderecos()) {
            endereco.setPacientes(Arrays.asList(paciente));
        }
        enderecoRepository.saveAll(paciente.getEnderecos());
        paciente.setId(null);
        return pacienteRepository.save(paciente);
    }

    public Paciente updatePaciente(Paciente Paciente) {
        findPaciente(Paciente.getId());
        return pacienteRepository.save(Paciente);
    }

    public void deletePaciente(Long id) {
        findPaciente(id);
        try {
            pacienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}