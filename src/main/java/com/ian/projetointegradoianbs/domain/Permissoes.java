package com.ian.projetointegradoianbs.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ian.projetointegradoianbs.domain.enuns.TipoUsuario;

import lombok.Data;

@Data
@Entity
public class Permissoes implements Serializable {

    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

}
