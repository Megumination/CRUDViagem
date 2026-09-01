package com.template.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.template.util.DialogUtil.showWarning;

public class ViagemValidator {

    public static boolean validarViagem(
            String destino,
            String preco,
            Date data_ida,
            Date data_volta,
            String observacoes) {

        // Lista de validadores
        List<Validador<String>> validadores = new ArrayList<>();

        // Validação do destino
        validadores.add(
                new CampoObrigatorioValidador("Destino", destino)
        );

        // Validação do preço
        validadores.add(
                new CampoObrigatorioValidador("Preco", preco)
        );

        // Percorre os validadores
        for (Validador<String> validador : validadores) {

            if (!validador.validar(validador.getValor())) {
                showWarning(validador.getMensagemErro());
                return false;
            }
        }

        // O destino deve ter pelo menos 3 caracteres
        if (destino.trim().length() < 3) {
            showWarning("O destino deve ter pelo menos 3 caracteres!");
            return false;
        }

        // O preço precisa ser um número válido
        double valor;

        try {
            valor = Double.parseDouble(preco);
        } catch (NumberFormatException e) {
            showWarning("Digite um preco valido!");
            return false;
        }

        // O preço deve ser maior que zero
        if (valor <= 0) {
            showWarning("O preço deve ser maior que zero!");
            return false;
        }

        // Data de ida obrigatória
        if (data_ida == null) {
            showWarning("Digite uma data de ida!");
            return false;
        }

        // Data de volta obrigatória
        if (data_volta == null) {
            showWarning("Digite uma data de volta!");
            return false;
        }

        // A data de volta não pode ser anterior à data de ida
        if (data_volta.before(data_ida)) {
            showWarning(
                    "A data de volta nao pode ser anterior a data de ida!"
            );
            return false;
        }

        // Observações podem ser vazias, mas não podem passar de 500 caracteres
        if (observacoes != null && observacoes.length() > 500) {
            showWarning(
                    "As observacoes devem ter no maximo 500 caracteres!"
            );
            return false;
        }

        // Todas as validações foram aprovadas
        return true;
    }
}