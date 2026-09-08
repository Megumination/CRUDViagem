package com.template.validator;

import java.util.Date;

public class DataValidator implements Validador<String> {

    private final String valor;
    private final Date dataIda;
    private final Date dataVolta;

    public DataValidator(Date dataIda, Date dataVolta) {
        this.dataIda = dataIda;
        this.dataVolta = dataVolta;
        this.valor = "datas";
    }

    @Override
    public boolean validar(String valor) {

        if (dataIda == null) {
            return false;
        }

        if (dataVolta == null) {
            return false;
        }

        return !dataVolta.before(dataIda);
    }

    @Override
    public String getMensagemErro() {

        if (dataIda == null) {
            return "Digite uma data de ida!";
        }

        if (dataVolta == null) {
            return "Digite uma data de volta!";
        }

        if (dataVolta.before(dataIda)) {
            return "A data de volta nao pode ser anterior a data de ida!";
        }

        return "";
    }

    @Override
    public String getValor() {
        return valor;
    }
}