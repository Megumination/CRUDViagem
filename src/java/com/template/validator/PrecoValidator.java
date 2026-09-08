package com.template.validator;

public class PrecoValidator implements Validador<String> {

    private final String valor;

    public PrecoValidator(String valor) {
        this.valor = valor;
    }

    @Override
    public boolean validar(String valor) {

        if (valor == null || valor.trim().isEmpty()) {
            return false;
        }

        try {
            double preco = Double.parseDouble(valor);
            return preco > 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getMensagemErro() {

        if (valor == null || valor.trim().isEmpty()) {
            return "O campo preco deve ser preenchido.";
        }

        try {
            double preco = Double.parseDouble(valor);

            if (preco <= 0) {
                return "O preco deve ser maior que zero!";
            }

        } catch (NumberFormatException e) {
            return "Digite um preco valido!";
        }

        return "";
    }

    @Override
    public String getValor() {
        return valor;
    }
}