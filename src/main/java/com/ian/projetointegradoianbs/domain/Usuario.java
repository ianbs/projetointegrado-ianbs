package com.ian.projetointegradoianbs.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ian.projetointegradoianbs.domain.enuns.TipoUsuario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode
@NoArgsConstructor
public class Usuario implements Serializable {

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
    private String senha;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private Integer tipoUsuario;

    public Usuario(Long id, String nome, String senha, String email, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.tipoUsuario = tipoUsuario.getId();
    }

}
