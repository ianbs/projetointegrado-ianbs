package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;

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

    public List<Convenio> findAllConvenios() {
        List<Convenio> convenios = convenioRepository.findAll();
        return convenios;
    }

    public Convenio findConvenioByName(String nome) {
        Optional<Convenio> optional = convenioRepository.findByNome(nome);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Convênio não encontrado."));
    }

    public Convenio findConvenio(Integer id) {
        Optional<Convenio> optional = convenioRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException("Convênio não encontrado."));
    }

    public Convenio insertConvenio(Convenio convenio) {
        return convenioRepository.save(convenio);
    }

    public Convenio updateConvenio(Convenio convenio) {
        findConvenio(convenio.getId());
        return convenioRepository.save(convenio);
    }

    public void deleteConvenio(Integer id) {
        try {
            convenioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}
