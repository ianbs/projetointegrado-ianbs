package com.ian.projetointegradoianbs.repository;

import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Procedimento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
    public Optional<Procedimento> findByNome(String nome);

    public Optional<Procedimento> findByCodigoTuss(String codigoTuss);

    public Optional<Procedimento> findByCodigoAmb(String codigoAmb);

    public Optional<Procedimento> findByCodigoCbhpm(String codigoCbhpm);
}
