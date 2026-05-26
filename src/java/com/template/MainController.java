package com.template;

import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;

public class MainController {

    @FXML private Button btnCadastrar;
    @FXML private Button btnAlterar;
    @FXML private Button btnExcluir;
    @FXML private TextField txtDestino;
    @FXML private TextField txtPreco;
    @FXML private TextField txtDataVolta;
    @FXML private TextField txtDataIda;
    @FXML private TextField txtObservacoes;
    @FXML private TableView <ViagemDTO> tblViagem;
    @FXML private TableColumn <ViagemDTO, Integer> colID;
    @FXML private TableColumn <ViagemDTO, Integer> colDestino;
    @FXML private TableColumn <ViagemDTO, Integer> colPreco;
    @FXML private TableColumn <ViagemDTO, Integer> colDataIda;
    @FXML private TableColumn <ViagemDTO, Integer> colDataVolta;
    @FXML private TableColumn <ViagemDTO, Integer> colObservacoes;

    // fazer o código dos botões

    @FXML
    private void initializa() { System.out.println("FXML loaded sucess")}
}