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
}