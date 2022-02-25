package com.ian.projetointegradoianbs.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Endereco;
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

    @Autowired
    private EnderecoServices enderecoServices;

    public Profissional findUsuario(Integer id) throws IOException {
        Optional<Profissional> optional = profissionalRepository.findByUsuario(id);
        return optional.orElseThrow(() -> new IOException("Não encontrado"));
    }

    public List<Profissional> findAllProfissionais() {
        List<Profissional> obj = profissionalRepository.findAll();
        return obj;
    }

    public Profissional findProfissional(Long id) {
        Optional<Profissional> optional = profissionalRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Profissional.class.getName()));
    }

    public Profissional insertProfissional(Profissional profissional) {
        List<Endereco> enderecos = enderecoServices.insertAllEnderecos(profissional.getEnderecos());
        profissional.setEnderecos(enderecos);
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
