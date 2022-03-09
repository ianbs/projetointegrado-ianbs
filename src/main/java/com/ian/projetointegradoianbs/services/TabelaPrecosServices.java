package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Convenio;
import com.ian.projetointegradoianbs.domain.TabelaPreco;
import com.ian.projetointegradoianbs.repository.TabelaPrecoRepository;
import com.ian.projetointegradoianbs.services.exceptions.DataIntegrityException;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TabelaPrecosServices {
    @Autowired
    private TabelaPrecoRepository tabelaPrecoRepository;

    public List<TabelaPreco> findAll() {
        return tabelaPrecoRepository.findAll();
    }

    public TabelaPreco findById(Integer id) {
        Optional<TabelaPreco> optional = tabelaPrecoRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Tabela de preço não encontrada."));
    }

    public List<TabelaPreco> findByConvenio(Convenio convenio) {
        return tabelaPrecoRepository.findByConvenio(convenio);
    }

    public TabelaPreco save(TabelaPreco tabelaPreco) {
        return tabelaPrecoRepository.save(tabelaPreco);
    }

    public TabelaPreco update(TabelaPreco tabelaPreco) {
        findById(tabelaPreco.getId());
        return tabelaPrecoRepository.save(tabelaPreco);
    }

    public void delete(Integer id) {
        try {
            tabelaPrecoRepository.deleteById(id);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}
