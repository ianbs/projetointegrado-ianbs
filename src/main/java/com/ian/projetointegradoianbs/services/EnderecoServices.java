package com.ian.projetointegradoianbs.services;

import java.util.List;

import com.ian.projetointegradoianbs.domain.Cidade;
import com.ian.projetointegradoianbs.domain.Endereco;
import com.ian.projetointegradoianbs.repository.EnderecoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoServices {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private CidadeServices cidadeServices;

    public List<Endereco> insertAllEnderecos(List<Endereco> enderecos) {
        for (Endereco endereco : enderecos) {
            Cidade cidade = cidadeServices.findCidadeByNome(endereco.getCidade());
            endereco.setCidade(cidade);
        }
        return enderecoRepository.saveAll(enderecos);
    }

}
