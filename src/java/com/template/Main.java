package com.template;

import com.template.controller.MainController;
import com.template.validator.IViagemValidator;
import com.template.validator.ViagemValidator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Cria a implementação concreta do validador
        IViagemValidator vValidator = new ViagemValidator();

        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource("main.fxml")
        );

        // Injeção de dependência
        loader.setControllerFactory(controllerClass -> {

            if (controllerClass == MainController.class) {
                return new MainController(vValidator);
            }

            try {
                return controllerClass
                        .getDeclaredConstructor()
                        .newInstance();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);

        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}