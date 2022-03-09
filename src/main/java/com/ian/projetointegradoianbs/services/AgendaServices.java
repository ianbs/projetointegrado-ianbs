package com.ian.projetointegradoianbs.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Agenda;
import com.ian.projetointegradoianbs.domain.Profissional;
import com.ian.projetointegradoianbs.repository.AgendaRepository;
import com.ian.projetointegradoianbs.services.exceptions.DataIntegrityException;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaServices {
    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private ProfissionalServices profissionalServices;

    public List<Agenda> findAll() {
        return agendaRepository.findAll();
    }

    public Agenda findById(UUID id) {
        Optional<Agenda> optional = agendaRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Agendamento não encontrado"));
    }

    public Agenda findByDescricao(String descricao) {
        Optional<Agenda> optional = agendaRepository.findByDescricao(descricao);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Nenhuma agedamento encontrado."));
    }

    public List<Agenda> findByData(LocalDate data) {
        Optional<List<Agenda>> optional = agendaRepository.findByData(data);
        return optional
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Nenhum agendamento encontrado nesta data."));
    }

    public List<Agenda> findByProfissional(UUID id) {
        Profissional profissional = profissionalServices.findById(id);
        Optional<List<Agenda>> optional = agendaRepository.findByProfissional(profissional);
        return optional.orElseThrow(
                () -> new ObjetoNaoEncontradoException("Nenhum agendamento encontrado para o profissional informado."));
    }

    public Agenda save(Agenda agenda) {
        Profissional profissional = profissionalServices.findById(agenda.getProfissional().getId());
        agenda.setProfissional(profissional);
        return agendaRepository.save(agenda);
    }

    public Agenda update(Agenda agenda) {
        findById(agenda.getId());
        Profissional profissional = profissionalServices.findById(agenda.getProfissional().getId());
        agenda.setProfissional(profissional);
        return agendaRepository.save(agenda);
    }

    public void delete(UUID id) {
        try {
            agendaRepository.deleteById(id);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}
