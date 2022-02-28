package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Cidade;
import com.ian.projetointegradoianbs.domain.Estado;
import com.ian.projetointegradoianbs.repository.CidadeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CidadeServices {
    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoServices estadoServices;

    public List<Cidade> findAllCidades() {
        List<Cidade> cidades = cidadeRepository.findAll();
        return cidades;
    }

    public Cidade findCidade(Cidade cidade) {
        Optional<Cidade> optional = cidadeRepository.findById(cidade.getId());
        return optional.orElse(null);
    }

    public Cidade findCidadeByNome(Cidade cidade) {
        return cidadeRepository.findByNome(cidade.getNome());
    }

    public Cidade insertCidade(Cidade cidade) {
        Estado estado = estadoServices.insertEstado(cidade.getEstado());
        cidade.setEstado(estado);
        return cidadeRepository.save(cidade);
    }

    public Cidade updateCidade(Cidade cidade) {
        findCidade(cidade);
        return cidadeRepository.save(cidade);
    }

    public void deleteEstado(Cidade cidade) {
        findCidade(cidade);
        cidadeRepository.delete(cidade);
    }
}
