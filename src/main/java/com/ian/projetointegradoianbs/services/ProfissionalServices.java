package com.ian.projetointegradoianbs.services;

import java.io.IOException;
import java.util.Optional;

import com.ian.projetointegradoianbs.domain.Profissional;
import com.ian.projetointegradoianbs.repository.ProfissionalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfissionalServices {
    @Autowired
    private ProfissionalRepository profissionalRepository;

    public Profissional findUsuario(Long id) throws IOException {
        Optional<Profissional> optional = profissionalRepository.findByUsuario(id);
        return optional.orElseThrow(() -> new IOException("Não encontrado"));
    }
}
