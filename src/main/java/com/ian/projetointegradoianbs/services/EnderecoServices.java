package com.ian.projetointegradoianbs.services;

import java.util.List;
import java.util.Optional;

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

    public Endereco findEndereco(Integer id) {
        Optional<Endereco> optional = enderecoRepository.findById(id);
        return optional.orElseThrow(() -> new ObjetoNaoEncontradoException(
                "Objeto não encontrado. ID: " + id + ", Tipo: " + Endereco.class.getName()));
    }

    public List<Endereco> insertAllEnderecos(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            Cidade cidade = cidadeServices.findCidadeByNome(endereco.getCidade());
            endereco.setCidade(cidade);
        }
        return enderecoRepository.saveAll(enderecos);
    }

    public List<Endereco> updateAllEnderecos(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            findEndereco(endereco.getId());
            Cidade cidade = cidadeServices.findCidadeByNome(endereco.getCidade());
            endereco.setCidade(cidade);
        }
        return enderecoRepository.saveAll(enderecos);
    }

    public void deleteAllEnderecos(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            findEndereco(endereco.getId());
            enderecoRepository.deleteById(endereco.getId());
        }
    }

}
