package com.template.validator;

public class DestinoValidator implements Validador<String> {

    private final String valor;

    public DestinoValidator(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {

        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        return valor.trim().length() >= 3;
    }

    @Override
    public String getMensagemErro() {

        if (valor == null || valor.trim().isEmpty()) {
            return "O campo Destino deve ser preenchido.";
        }

        return "O destino deve ter pelo menos 3 caracteres!";
    }

    @Override
    public String getValor() {
        return valor;
    }
}