package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.ian.projetointegradoianbs.domain.Cidade;
import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.repository.EnderecoRepository;
import com.ian.projetointegradoianbs.services.exceptions.ObjetoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServices {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeServices cidadeServices;

    public Endereco findById(Integer id) {
        Optional<Endereco> optional = enderecoRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Endereco.class.getName()));
    }

    @Transactional
    public List<Endereco> save(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            Cidade cidade = cidadeServices.findByNome(endereco.getCidade());
            endereco.setCidade(cidade);
        }
        return enderecoRepository.saveAll(enderecos);
    }

    public List<Endereco> update(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            findById(endereco.getId());
            Cidade cidade = cidadeServices.findByNome(endereco.getCidade());
            endereco.setCidade(cidade);
        }
        return enderecoRepository.saveAll(enderecos);
    }

    public void delete(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            findById(endereco.getId());
            enderecoRepository.deleteById(endereco.getId());
        }
    }

}
