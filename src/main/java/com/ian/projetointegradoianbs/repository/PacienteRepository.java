package com.ian.projetointegradoianbs.repository;

import com.ian.projetointegradoianbs.domain.Paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
