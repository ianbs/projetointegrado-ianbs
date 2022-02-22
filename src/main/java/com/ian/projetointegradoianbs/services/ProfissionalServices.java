package com.ian.projetointegradoianbs.services;

import java.io.IOException;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Profissional;
import com.ian.projetointegradoianbs.repository.ProfissionalRepository;
import com.ian.projetointegradoianbs.services.exceptions.DataIntegrityException;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalServices {
    @Autowired
    private ProfissionalRepository profissionalRepository;

    public Profissional findUsuario(Integer id) throws IOException {
        Optional<Profissional> optional = profissionalRepository.findByUsuario(id);
        return optional.orElseThrow(() -> new IOException("Não encontrado"));
    }

    public Profissional findProfissional(Long id) {
        Optional<Profissional> optional = profissionalRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Profissional.class.getName()));
    }

    public Profissional insertProfissional(Profissional profissional) {
        profissional.setId(null);
        return profissionalRepository.save(profissional);
    }

    public Profissional updateProfissional(Profissional profissional) {
        findProfissional(profissional.getId());
        return profissionalRepository.save(profissional);
    }

    public void deleteProfissional(Long id) {
        findProfissional(id);
        try {
            profissionalRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}
