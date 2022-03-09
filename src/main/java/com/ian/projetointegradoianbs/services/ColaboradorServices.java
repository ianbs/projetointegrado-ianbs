package com.ian.projetointegradoianbs.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ian.projetointegradoianbs.domain.Colaborador;
import com.ian.projetointegradoianbs.repository.ColaboradorRepository;
import com.ian.projetointegradoianbs.services.exceptions.DataIntegrityException;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorServices {
    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public Colaborador findUsuario(UUID id) throws IOException {
        Optional<Colaborador> optional = colaboradorRepository.findByUsuario(id);
        return optional.orElseThrow(() -> new IOException("Colaborador não encontrado"));
    }

    public List<Colaborador> findAll() {
        List<Colaborador> obj = colaboradorRepository.findAll();
        return obj;
    }

    public Colaborador findById(UUID id) {
        Optional<Colaborador> optional = colaboradorRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Colaborador.class.getName()));
    }

    public Colaborador save(Colaborador colaborador) {
        return colaboradorRepository.save(colaborador);
    }

    public Colaborador update(Colaborador colaborador) {
        findById(colaborador.getId());
        return colaboradorRepository.save(colaborador);
    }

    public void delete(UUID id) {
        try {
            colaboradorRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataIntegrityException("Não é possivel excluir. Erro: " + e.getMessage());
        }
    }
}
