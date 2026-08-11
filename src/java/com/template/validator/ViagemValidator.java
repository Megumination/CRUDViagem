package com.template.validator;

import java.util.Date;

import static com.template.util.DialogUtil.showWarning;

public class ViagemValidator {

    public static boolean validarViagem( String destino, String preco, Date data_ida, Date data_volta, String observacoes) {

        if (destino == null || destino.trim().isEmpty()) {
            showWarning("Digite o destino da sua viagem!");
            return false;
        }

        if (destino.trim().length() < 3) {
            showWarning("O destino deve ter pelo menos 3 caracteres!");
            return false;
        }

        if (preco == null || preco.trim().isEmpty()) {
            showWarning("Digite o preco!");
            return false;
        }

        double valor;

        try {
            valor = Double.parseDouble(preco);
        } catch (NumberFormatException e) {
            showWarning("Digite um preco valido!");
            return false;
        }

        if (valor <= 0) {
            showWarning("O preco deve ser maior que zero!");
            return false;
        }

        if (data_ida == null) {
            showWarning("Digite uma data de ida!");
            return false;
        }

        if (data_volta == null) {
            showWarning("Digite uma data de volta!");
            return false;
        }

        if (data_volta.before(data_ida)) {
            showWarning("A data de volta nao pode ser anterior a data de ida!");
            return false;
        }

        if (observacoes != null && observacoes.length() > 500) {
            showWarning("As observacoes devem ter no maximo 500 caracteres!");
            return false;
        }

        return true;
    }
}