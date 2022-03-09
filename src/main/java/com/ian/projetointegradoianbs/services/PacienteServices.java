package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Paciente;
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

    public List<Paciente> findAll() {
        List<Paciente> obj = pacienteRepository.findAll();
        return obj;
    }

    public Paciente findById(UUID id) {
        Optional<Paciente> optional = pacienteRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Paciente.class.getName()));
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Paciente update(Paciente Paciente) {
        findById(Paciente.getId());
        return pacienteRepository.save(Paciente);
    }

    public void delete(Paciente paciente) {
        try {
            pacienteRepository.deleteById(paciente.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}