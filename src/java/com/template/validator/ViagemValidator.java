package com.template.validator;

import java.util.Date;

import static com.template.util.DialogUtil.showWarning;

public class ViagemValidator implements IViagemValidator {

    @Override
    public boolean validarViagem(
            String destino,
            String preco,
            Date dataIda,
            Date dataVolta,
            String observacoes) {

        // Validação do destino
        if (!validarDestino(destino)) {

            if (destino == null || destino.trim().isEmpty()) {
                showWarning("O campo Destino deve ser preenchido.");
            } else {
                showWarning("O destino deve ter pelo menos 3 caracteres!");
            }

            return false;
        }

        // Validação do preço
        if (!validarPreco(preco)) {

            if (preco == null || preco.trim().isEmpty()) {
                showWarning("O campo Preco deve ser preenchido.");
            } else {
                try {
                    double valor = Double.parseDouble(preco);

                    if (valor <= 0) {
                        showWarning("O preco deve ser maior que zero!");
                    }

                } catch (NumberFormatException e) {
                    showWarning("Digite um preço valido!");
                }
            }

            return false;
        }

        // Validação da data de ida
        if (!validarDataIda(dataIda)) {
            showWarning("Digite uma data de ida!");
            return false;
        }

        // Validação da data de volta
        if (!validarDataVolta(dataVolta)) {
            showWarning("Digite uma data de volta!");
            return false;
        }

        // Validação das datas
        if (!validarDatas(dataIda, dataVolta)) {
            showWarning(
                    "A data de volta nao pode ser anterior a data de ida!"
            );
            return false;
        }

        // Validação das observações
        if (!validarObservacoes(observacoes)) {
            showWarning(
                    "As observacoes devem ter no maximo 500 caracteres!"
            );
            return false;
        }

        return true;
    }

    @Override
    public boolean validarDestino(String destino) {

        if (destino == null || destino.trim().isEmpty()) {
            return false;
        }

        return destino.trim().length() >= 3;
    }

    @Override
    public boolean validarPreco(String preco) {

        if (preco == null || preco.trim().isEmpty()) {
            return false;
        }

        try {
            double valor = Double.parseDouble(preco);

            return valor > 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean validarDataIda(Date dataIda) {
        return dataIda != null;
    }

    @Override
    public boolean validarDataVolta(Date dataVolta) {
        return dataVolta != null;
    }

    @Override
    public boolean validarDatas(Date dataIda, Date dataVolta) {

        if (dataIda == null || dataVolta == null) {
            return false;
        }

        return !dataVolta.before(dataIda);
    }

    @Override
    public boolean validarObservacoes(String observacoes) {

        if (observacoes == null) {
            return true;
        }

        return observacoes.length() <= 500;
    }
}