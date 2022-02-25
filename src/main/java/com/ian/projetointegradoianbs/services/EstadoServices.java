package com.ian.projetointegradoianbs.services;

import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Estado;
import com.ian.projetointegradoianbs.repository.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoServices {
    @Autowired
    private EstadoRepository estadoRepository;

    public Estado findEstado(Estado estado) {
        Optional<Estado> optional = estadoRepository.findById(estado.getId());
        return optional.orElse(null);
    }

    public Estado insertEstado(Estado estado) {
        return estadoRepository.save(estado);
    }

}
