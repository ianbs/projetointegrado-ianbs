package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;

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

    public List<Colaborador> findAllColaboradores() {
        List<Colaborador> obj = colaboradorRepository.findAll();
        return obj;
    }

    public Colaborador findColaborador(Long id) {
        Optional<Colaborador> optional = colaboradorRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Colaborador.class.getName()));
    }

    public Colaborador insertColaborador(Colaborador colaborador) {
        colaborador.setId(null);
        return colaboradorRepository.save(colaborador);
    }

    public Colaborador updateColaborador(Colaborador colaborador) {
        findColaborador(colaborador.getId());
        return colaboradorRepository.save(colaborador);
    }

    public void deleteColaborador(Long id) {
        findColaborador(id);

        try {
            colaboradorRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataIntegrityException("Não é possivel excluir");
        }

    }
}
