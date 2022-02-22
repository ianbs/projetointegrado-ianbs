package com.ian.projetointegradoianbs.domain.enuns;

public enum Tipo {
    COLABORADOR(1, "Colaborador"), PROFISSIONAL(2, "Cancelado");

    private int codigo;

    private String descricao;

    private Tipo(int codigo, String descricao) {
        this.setCodigo(codigo);
        this.setDescricao(descricao);
    }

    public static Tipo toEnum(Integer codigo) {
        if (codigo == null) {
            return null;
        }
        for (Tipo tipo : Tipo.values()) {
            if (codigo.equals(tipo.getCodigo())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
