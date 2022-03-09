package com.ian.projetointegradoianbs.repository;

import java.util.List;

import com.ian.projetointegradoianbs.domain.Convenio;
import com.ian.projetointegradoianbs.domain.Procedimento;
import com.ian.projetointegradoianbs.domain.TabelaPreco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabelaPrecoRepository extends JpaRepository<TabelaPreco, Integer> {
    TabelaPreco findByProcedimento(Procedimento procedimento);

    List<TabelaPreco> findByConvenio(Convenio convenio);
}
