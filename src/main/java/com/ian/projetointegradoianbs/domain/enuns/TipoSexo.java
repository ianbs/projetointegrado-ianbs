package com.ian.projetointegradoianbs.domain.enuns;

public enum TipoSexo {
    MASCULINO(1, "Masculino"),
    FEMININO(2, "Feminino"),
    OUTRO(3, "Outro");

    private int id;
    private String descricao;

    private TipoSexo(int id, String descricao) {
        this.setId(id);
        this.setDescricao(descricao);
    }

    public static TipoSexo toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (TipoSexo tipoSexo : TipoSexo.values()) {
            if (codigo.equals(tipoSexo.getId())) {
                return tipoSexo;
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
