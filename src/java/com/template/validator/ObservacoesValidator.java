package com.template.validator;

public class ObservacoesValidator implements Validador<String> {

    private final String valor;

    public ObservacoesValidator(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {

        if (valor == null) {
            return true;
        }

        return valor.length() <= 500;
    }

    @Override
    public String getMensagemErro() {
        return "As observacoes devem ter no maximo 500 caracteres!";
    }

    @Override
    public String getValor() {
        return valor;
    }
}