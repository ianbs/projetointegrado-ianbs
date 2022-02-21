package com.ian.projetointegradoianbs.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Profissional implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    @Setter
    private String razaoSocial;
    @Getter
    @Setter
    private String conselho;
    @Getter
    @Setter
    private String numeroConselho;
    @Getter
    @Setter
    private String especialidade;
    @Getter
    @Setter
    private Integer cbo;
    @Getter
    @Setter
    private String cpf;
    @Getter
    @Setter
    private String cnpj;
    @Getter
    @Setter
    private String rg;
    @Getter
    @Setter
    private Date dataVinculo;
    @Getter
    @Setter
    private Date dataNascimento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
