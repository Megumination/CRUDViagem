package com.template.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DialogUtil {

    public static void showInformation(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Informação");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static boolean showConfirmation(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);

        return alerta.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }

    public static void showWarning(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void showError(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Erro");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}