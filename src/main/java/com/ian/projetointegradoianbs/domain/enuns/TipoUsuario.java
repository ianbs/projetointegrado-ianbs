package com.ian.projetointegradoianbs.domain.enuns;

import org.springframework.security.core.GrantedAuthority;

public enum TipoUsuario implements GrantedAuthority {
    ADMINISTRADOR(1, "Administrador"),
    PADRAO(2, "Padrão");

    private int id;
    private String descricao;

    private TipoUsuario(int id, String descricao) {
        this.setId(id);
        this.setDescricao(descricao);
    }

    public static TipoUsuario toEnum(Integer id) {
        if (id == null) {
            return null;
        }
        for (TipoUsuario tipoUsuario : TipoUsuario.values()) {
            if (id.equals(tipoUsuario.getId())) {
                return tipoUsuario;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return name();
    }

}
