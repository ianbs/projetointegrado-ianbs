package com.ian.projetointegradoianbs.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ian.projetointegradoianbs.domain.enuns.TipoEndereco;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class Endereco implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String logradouro;
    private Integer numero;
    private String cep;
    private String bairro;
    private String complemento;
    private Integer codigoIbge;

    private Integer tipoEndereco;

    @ManyToOne
    @JoinColumn(name = "id_cidade")
    private Cidade cidade;

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos", cascade = CascadeType.ALL)
    private List<Profissional> profissionais = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos", cascade = CascadeType.ALL)
    private List<Paciente> pacientes = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "enderecos", cascade = CascadeType.ALL)
    private List<Colaborador> colaborador = new ArrayList<>();

    public Endereco(Integer id, String logradouro, Integer numero, String complemento, Integer codigoIbge,
            TipoEndereco tipoEndereco, Cidade cidade) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.codigoIbge = codigoIbge;
        this.tipoEndereco = tipoEndereco.getId();
        this.cidade = cidade;
    }

}
