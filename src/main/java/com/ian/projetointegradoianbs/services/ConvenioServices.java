package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Convenio;
import com.ian.projetointegradoianbs.repository.ConvenioRepository;
import com.ian.projetointegradoianbs.services.exceptions.DataIntegrityException;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ConvenioServices {

    @Autowired
    private ConvenioRepository convenioRepository;

    public List<Convenio> findAll() {
        List<Convenio> convenios = convenioRepository.findAll();
        return convenios;
    }

    public Convenio findByName(String nome) {
        Optional<Convenio> optional = convenioRepository.findByNome(nome);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Convênio não encontrado."));
    }

    public Convenio findById(UUID id) {
        Optional<Convenio> optional = convenioRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Convênio não encontrado."));
    }

    public Convenio save(Convenio convenio) {
        return convenioRepository.save(convenio);
    }

    public Convenio update(Convenio convenio) {
        findById(convenio.getId());
        return convenioRepository.save(convenio);
    }

    public void delete(UUID id) {
        try {
            convenioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}
