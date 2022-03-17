package com.ian.projetointegradoianbs.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ian.projetointegradoianbs.domain.enuns.TipoSexo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Profissional implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String nome;

    @NotBlank
    private String conselho;

    @NotBlank
    private String numeroConselho;

    @NotBlank
    private String especialidade;

    private Integer cbo;

    @NotBlank
    private String cpf;

    @NotBlank
    private String rg;

    private LocalDateTime dataVinculo;

    private LocalDate dataNascimento;

    @Enumerated(EnumType.ORDINAL)
    private TipoSexo sexo;

    @OneToOne(mappedBy = "profissional", cascade = CascadeType.ALL)
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "PROFISSIONAL_ENDERECOS", joinColumns = @JoinColumn(name = "id_profissional"), inverseJoinColumns = @JoinColumn(name = "id_endereco"))
    private List<Endereco> enderecos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "profissional")
    private List<Agenda> agenda = new ArrayList<>();
}
