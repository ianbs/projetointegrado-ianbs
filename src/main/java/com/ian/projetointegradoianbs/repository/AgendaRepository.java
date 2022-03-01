package com.ian.projetointegradoianbs.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Agenda;
import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.domain.Paciente;
import com.ian.projetointegradoianbs.domain.Profissional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Integer> {
    Optional<Agenda> findByDescricao(String descricao);

    Optional<List<Agenda>> findByData(LocalDate data);

    Optional<List<Agenda>> findByPaciente(Paciente paciente);

    Optional<List<Agenda>> findByColaborador(Colaborador colaborador);

    Optional<List<Agenda>> findByProfissional(Profissional profissional);
}
