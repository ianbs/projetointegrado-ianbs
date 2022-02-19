package com.ian.projetointegradoianbs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Paciente implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    @Setter
    private String nome;
    @Getter
    @Setter
    private String cpf;
    @Getter
    @Setter
    private String rg;
    @Getter
    @Setter
    private String cartaoNacionalSaude;
    @Getter
    @Setter
    private Date dataCadastro;
    @Getter
    @Setter
    private Date dataNascimento;

}
