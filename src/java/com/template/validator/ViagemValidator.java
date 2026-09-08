package com.template.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.template.util.DialogUtil.showWarning;

public class ViagemValidator implements IViagemValidator {

    @Override
    public boolean validarViagem(
            String destino,
            String preco,
            Date dataIda,
            Date dataVolta,
            String observacoes) {

        // Lista de validadores
        List<Validador<String>> validadores = new ArrayList<>();

        // Adicionando os validadores
        String dataIdaTexto = dataIda == null ? "" : dataIda.toString();
        String dataVoltaTexto = dataVolta == null ? "" : dataVolta.toString();

        validadores.add(new CampoObrigatorioValidador("Destino", destino));
        validadores.add(new CampoObrigatorioValidador("Preço", preco));
        validadores.add(new CampoObrigatorioValidador("Data de Ida", dataIdaTexto));
        validadores.add(new CampoObrigatorioValidador("Data de Volta", dataVoltaTexto));

        validadores.add(new DestinoValidator(destino));
        validadores.add(new PrecoValidator(preco));
        validadores.add(new DataValidator(dataIda, dataVolta));
        validadores.add(new ObservacoesValidator(observacoes));

        // Percorre a lista de validadores
        for (Validador<String> validador : validadores) {

            // Cada validador testa seu próprio valor
            if (!validador.validar(validador.getValor())) {

                showWarning(validador.getMensagemErro());

                return false;
            }
        }

        return true;
    }
}