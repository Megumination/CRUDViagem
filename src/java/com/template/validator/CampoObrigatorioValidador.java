package com.template.validator;

public class CampoObrigatorioValidador implements Validador<String>{
    private final String nomeCampo;
    private final String valor;

    public CampoObrigatorioValidador(String nomeCampo, String valor) {
        this.nomeCampo = nomeCampo;
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {
        return this.valor != null && !this.valor.trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "O campo " + nomeCampo + "deve ser preenchido";
    }

    @Override
    public String getValor() {
        return valor;
    }
}

public class ViagemValidator implements Validador<String> {

    private final String destino;

    public ViagemValidator(String destino) {
        this.destino = destino;
    }

    @Override
    public boolean validar(String valor) {
        return this.destino != null && !this.destino.trim().isEmpty();
    }

    @Override
    public String getMensagemErro() {
        return "O destino da viagem deve ser preenchido.";
    }

    @Override
    public String getValor() {
        return destino;
    }
}