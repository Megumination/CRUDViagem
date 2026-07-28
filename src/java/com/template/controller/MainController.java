package com.template.controller;

import java.util.ArrayList;
import java.util.Date;

import com.template.model.dao.ViagemDAO;
import com.template.model.dto.ViagemDTO;

import static com.template.util.DialogUtil.*;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML
    private Button btnCadastrar;

    @FXML
    private Button btnAlterar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLimpar;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField txtDestino;

    @FXML
    private TextField txtPreco;

    @FXML
    private TextField txtObservacoes;

    @FXML
    private TextField txtPesquisa;

    @FXML
    private DatePicker dtpDataIda;

    @FXML
    private DatePicker dtpDataVolta;

    @FXML
    private TableView<ViagemDTO> tblViagem;

    @FXML
    private TableColumn<ViagemDTO, Integer> colID;

    @FXML
    private TableColumn<ViagemDTO, String> colDestino;

    @FXML
    private TableColumn<ViagemDTO, Double> colPreco;

    @FXML
    private TableColumn<ViagemDTO, Date> colDataIda;

    @FXML
    private TableColumn<ViagemDTO, Date> colDataVolta;

    @FXML
    private TableColumn<ViagemDTO, String> colObservacoes;


    @FXML
    private void btnCadastrarAction(ActionEvent event) {

        if (txtDestino.getText().trim().isEmpty()) {
            mostrarMensagem("O destino é obrigatório!", false);
            return;
        }

        if (txtPreco.getText().trim().isEmpty()) {
            mostrarMensagem("O preço é obrigatório!", false);
            return;
        }

        if (dtpDataIda.getValue() == null) {
            mostrarMensagem("Informe a data de ida!", false);
            return;
        }

        if (dtpDataVolta.getValue() == null) {
            mostrarMensagem("Informe a data de volta!", false);
            return;
        }

        ViagemDTO novaViagem = new ViagemDTO();

        novaViagem.setDestino(txtDestino.getText());
        novaViagem.setPreco(Double.parseDouble(txtPreco.getText()));
        novaViagem.setDataIda(java.sql.Date.valueOf(dtpDataIda.getValue()));
        novaViagem.setDataVolta(java.sql.Date.valueOf(dtpDataVolta.getValue()));
        novaViagem.setObservacoes(txtObservacoes.getText());

        ViagemDAO viagemDAO = new ViagemDAO();
        viagemDAO.cadastrar(novaViagem);

        mostrarMensagem("Viagem cadastrada com sucesso!", true);

        carregarViagem();
        limparCampos();
    }

    @FXML
    private void btnAlterarAction(ActionEvent event) {

        ViagemDTO viagem = tblViagem.getSelectionModel().getSelectedItem();

        if (viagem != null) {

            viagem.setDestino(txtDestino.getText());
            viagem.setPreco(Double.parseDouble(txtPreco.getText()));
            viagem.setDataIda(java.sql.Date.valueOf(dtpDataIda.getValue()));
            viagem.setDataVolta(java.sql.Date.valueOf(dtpDataVolta.getValue()));
            viagem.setObservacoes(txtObservacoes.getText());

            ViagemDAO viagemDAO = new ViagemDAO();
            viagemDAO.alterar(viagem);

            mostrarMensagem("Viagem alterada com sucesso!", true);

            carregarViagem();
            limparCampos();
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        ViagemDTO viagem = tblViagem.getSelectionModel().getSelectedItem();

        if (viagem == null) {
            mostrarMensagem("Selecione uma viagem para excluir!", false);
            return;
        }

        if (showConfirmation(
                "Deseja realmente excluir a viagem para " + viagem.getDestino() + "?")) {

            ViagemDAO viagemDAO = new ViagemDAO();
            viagemDAO.excluir(viagem.getId());

            mostrarMensagem("Viagem excluída com sucesso!", true);

            carregarViagem();
            limparCampos();
        }
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    private void limparCampos() {

        txtDestino.clear();
        txtPreco.clear();
        // Remove as datas selecionadas dos DatePickers
        dtpDataIda.setValue(null);
        dtpDataVolta.setValue(null);

        txtObservacoes.clear();
    }

    @FXML
    private void carregarViagem() {

        ViagemDAO viagemDAO = new ViagemDAO();

        // Busca todas as viagens cadastradas no banco
        ArrayList<ViagemDTO> listaViagens = viagemDAO.listarViagem();

        // Converte a ArrayList para ObservableList para exibir na TableView
        tblViagem.setItems(
                FXCollections.observableArrayList(listaViagens)
        );
    }

    @FXML
    private void initialize() {
        tblViagem.getSelectionModel().setCellSelectionEnabled(false);

        // Permite selecionar apenas uma linha da tabela por vez
        tblViagem.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);

        // Liga cada coluna ao atributo correspondente do DTO
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colDataIda.setCellValueFactory(new PropertyValueFactory<>("dataIda"));
        colDataVolta.setCellValueFactory(new PropertyValueFactory<>("dataVolta"));
        colObservacoes.setCellValueFactory(new PropertyValueFactory<>("observacoes"));

        //impede que o usuário digite qualquer coisa que não seja número ou ponto
        txtPreco.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*(\\.\\d*)?")) {
                txtPreco.setText(antigo);
            }
        });

        // Atualiza os campos automaticamente ao selecionar uma linha
        tblViagem.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, viagem) -> {

                    if (viagem != null) {

                        txtDestino.setText(viagem.getDestino());
                        txtPreco.setText(String.valueOf(viagem.getPreco()));
                        txtObservacoes.setText(viagem.getObservacoes());

                        dtpDataIda.setValue(
                                new java.sql.Date(viagem.getDataIda().getTime()).toLocalDate()
                        );

                        dtpDataVolta.setValue(
                                new java.sql.Date(viagem.getDataVolta().getTime()).toLocalDate()
                        );
                    }
                }
        );

        carregarViagem();
    }

    private void mostrarMensagem(String texto, boolean isSucesso) {

        // avisa o usuário se deu certo ou errado
        lblStatus.setText(texto);
        lblStatus.getStyleClass().removeAll("status-erro", "status-sucesso");
        if (isSucesso) {
            lblStatus.getStyleClass().add("status-sucesso");
        } else {
            lblStatus.getStyleClass().add("status-erro");
        }
    }

    @FXML
    private void carregarCampos() {

        // Pega a viagem selecionada na tabela
        ViagemDTO viagemSelecionada = tblViagem.getSelectionModel().getSelectedItem();

        if (viagemSelecionada != null) {

            txtDestino.setText(viagemSelecionada.getDestino());
            txtPreco.setText(String.valueOf(viagemSelecionada.getPreco()));
            txtObservacoes.setText(viagemSelecionada.getObservacoes());

            dtpDataIda.setValue(
                    new java.sql.Date(viagemSelecionada.getDataIda().getTime()).toLocalDate()
            );

            dtpDataVolta.setValue(
                    new java.sql.Date(viagemSelecionada.getDataVolta().getTime()).toLocalDate()
            );
        }
    }
}

