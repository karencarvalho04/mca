package br.sc.senac.mca.util;


public enum OperacoesCrud {

    NOVO(1), EDITAR(2), EXCLUIR(3);

    private final Integer operacao;

    private OperacoesCrud(Integer operacao) {
        this.operacao = operacao;
    }

    public Integer getOperacao() {
        return operacao;
    }

}

