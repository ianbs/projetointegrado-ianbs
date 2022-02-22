package com.ian.projetointegradoianbs.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Profissional implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
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

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "PROFISSIONAL_ENDERECOS", joinColumns = @JoinColumn(name = "id_profissional"), inverseJoinColumns = @JoinColumn(name = "id_endereco"))
    private List<Endereco> enderecos = new ArrayList<>();
}
