package com.template.validator;

import java.util.Date;

public interface IViagemValidator {

    boolean validarViagem(
            String destino,
            String preco,
            Date dataIda,
            Date dataVolta,
            String observacoes
    );

    boolean validarDestino(String destino);

    boolean validarPreco(String preco);

    boolean validarDataIda(Date dataIda);

    boolean validarDataVolta(Date dataVolta);

    boolean validarDatas(Date dataIda, Date dataVolta);

    boolean validarObservacoes(String observacoes);
}