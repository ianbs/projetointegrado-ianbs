package com.ian.projetointegradoianbs.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

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

    public Profissional findByUsuario(UUID id) throws IOException {
        Optional<Profissional> optional = profissionalRepository.findByUsuario(id);
        return optional.orElseThrow(() -> new IOException("Profissional não encontrado"));
    }

    public List<Profissional> findAll() {
        List<Profissional> obj = profissionalRepository.findAll();
        return obj;
    }

    public Profissional findById(UUID id) {
        Optional<Profissional> optional = profissionalRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Profissional.class.getName()));
    }

    @Transactional
    public Profissional save(Profissional profissional) {
        return profissionalRepository.save(profissional);
    }

    public Profissional update(Profissional profissional) {
        findById(profissional.getId());
        return profissionalRepository.save(profissional);
    }

    public void delete(UUID id) {
        try {
            profissionalRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir.");
        }
    }
}
