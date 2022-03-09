package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Procedimento;
import com.ian.projetointegradoianbs.repository.ProcedimentoRepository;
import com.ian.projetointegradoianbs.services.exceptions.DataIntegrityException;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcedimentoServices {
    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    public List<Procedimento> findAll() {
        List<Procedimento> procedimentos = procedimentoRepository.findAll();
        return procedimentos;
    }

    public Procedimento findById(Long id) {
        Optional<Procedimento> optional = procedimentoRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Procedimento não encontrado."));
    }

    public Procedimento findByCodigoTuss(String codigoTuss) {
        Optional<Procedimento> optional = procedimentoRepository.findByCodigoTuss(codigoTuss);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Procedimento não encontrado."));
    }

    public Procedimento save(Procedimento procedimento) {
        return procedimentoRepository.save(procedimento);
    }

    public Procedimento update(Procedimento procedimento) {
        findById(procedimento.getId());
        return procedimentoRepository.save(procedimento);
    }

    public void delete(Long id) {
        try {
            procedimentoRepository.deleteById(id);
        } catch (DataIntegrityException e) {
            throw new DataIntegrityException("Não é possivel excluir. " + e.getMessage());
        }
    }
}
