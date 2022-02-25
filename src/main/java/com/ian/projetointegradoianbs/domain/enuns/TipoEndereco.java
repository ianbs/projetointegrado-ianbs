package com.ian.projetointegradoianbs.domain.enuns;

public enum TipoEndereco {
    RESIDENCIAL(1, "Residencial"),
    COMERCIAL(2, "Comercial"),
    CONSULTORIO(3, "Consultorio");

    private int id;
    private String descricao;

    private TipoEndereco(int id, String descricao) {
        this.setId(id);
        this.setDescricao(descricao);
    }

    public static TipoEndereco toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (TipoEndereco tipoEndereco : TipoEndereco.values()) {
            if (codigo.equals(tipoEndereco.getId())) {
                return tipoEndereco;
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
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
