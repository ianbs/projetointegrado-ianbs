package com.ian.projetointegradoianbs.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Agenda;
import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.domain.Paciente;
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

    public List<Agenda> findAllAgenda() {
        List<Agenda> agendas = agendaRepository.findAll();
        return agendas;
    }

    public Agenda findAgenda(Integer id) {
        Optional<Agenda> optional = agendaRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Agendamento não encontrado"));
    }

    public Agenda findAgendaByDescricao(String descricao) {
        Optional<Agenda> optional = agendaRepository.findByDescricao(descricao);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Nenhuma agedamento encontrado."));
    }

    public List<Agenda> findAgendaByData(LocalDate data) {
        Optional<List<Agenda>> optional = agendaRepository.findByData(data);
        return optional
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Nenhum agendamento encontrado nesta data."));
    }

    public List<Agenda> findAgendaByPaciente(Paciente paciente) {
        Optional<List<Agenda>> optional = agendaRepository.findByPaciente(paciente);
        return optional.orElseThrow(
                () -> new ObjetoNaoEncontradoException("Nenhum agendamento encontrado para o paciente informado."));
    }

    public List<Agenda> findAgendaByColaborador(Colaborador colaborador) {
        Optional<List<Agenda>> optional = agendaRepository.findByColaborador(colaborador);
        return optional.orElseThrow(
                () -> new ObjetoNaoEncontradoException("Nenhum agendamento foi feito pelo colaborador informado."));
    }

    public List<Agenda> findAgendaByProfissional(Profissional profissional) {
        Optional<List<Agenda>> optional = agendaRepository.findByProfissional(profissional);
        return optional.orElseThrow(
                () -> new ObjetoNaoEncontradoException("Nenhum agendamento encontrado para o profissional informado."));
    }

    public Agenda insertAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

    public Agenda updateAgenda(Agenda agenda) {
        findAgenda(agenda.getId());
        return agendaRepository.save(agenda);
    }

    public void deleteAgenda(Integer id) {
        try {
            agendaRepository.deleteById(id);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}
