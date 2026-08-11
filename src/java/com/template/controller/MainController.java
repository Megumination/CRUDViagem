package com.template.controller;

import java.util.Date;
import java.util.ArrayList;

import com.template.model.dto.ViagemDTO;
import com.template.service.ViagemService;
import com.template.validator.ViagemValidator;

import static com.template.util.DialogUtil.showConfirmation;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

    private ViagemService viagemService = new ViagemService();


    @FXML
    private void btnCadastrarAction(ActionEvent event) {

        Date dataIda = null;
        Date dataVolta = null;

        if (dtpDataIda.getValue() != null) {
            dataIda = java.sql.Date.valueOf(dtpDataIda.getValue());
        }

        if (dtpDataVolta.getValue() != null) {
            dataVolta = java.sql.Date.valueOf(dtpDataVolta.getValue());
        }

        if (!ViagemValidator.validarViagem(
                txtDestino.getText(),
                txtPreco.getText(),
                dataIda,
                dataVolta,
                txtObservacoes.getText())) {
            return;
        }

        ViagemDTO novaViagem = new ViagemDTO();

        novaViagem.setDestino(txtDestino.getText());
        novaViagem.setPreco(Double.parseDouble(txtPreco.getText()));
        novaViagem.setDataIda(dataIda);
        novaViagem.setDataVolta(dataVolta);
        novaViagem.setObservacoes(txtObservacoes.getText());

        viagemService.cadastrar(novaViagem);

        mostrarMensagem("Viagem cadastrada com sucesso!", true);

        carregarViagem();
        limparCampos();
    }


    @FXML
    private void btnAlterarAction(ActionEvent event) {

        ViagemDTO viagem = tblViagem.getSelectionModel().getSelectedItem();

        if (viagem == null) {
            mostrarMensagem("Selecione uma viagem para alterar!", false);
            return;
        }

        Date dataIda = null;
        Date dataVolta = null;

        if (dtpDataIda.getValue() != null) {
            dataIda = java.sql.Date.valueOf(dtpDataIda.getValue());
        }

        if (dtpDataVolta.getValue() != null) {
            dataVolta = java.sql.Date.valueOf(dtpDataVolta.getValue());
        }

        if (!ViagemValidator.validarViagem(
                txtDestino.getText(),
                txtPreco.getText(),
                dataIda,
                dataVolta,
                txtObservacoes.getText())) {
            return;
        }

        viagem.setDestino(txtDestino.getText());
        viagem.setPreco(Double.parseDouble(txtPreco.getText()));
        viagem.setDataIda(dataIda);
        viagem.setDataVolta(dataVolta);
        viagem.setObservacoes(txtObservacoes.getText());

        viagemService.alterar(viagem);

        mostrarMensagem("Viagem alterada com sucesso!", true);

        carregarViagem();
        limparCampos();
    }


    @FXML
    private void btnExcluirAction(ActionEvent event) {

        ViagemDTO viagem = tblViagem.getSelectionModel().getSelectedItem();

        if (viagem == null) {
            mostrarMensagem("Selecione uma viagem para excluir!", false);
            return;
        }

        if (showConfirmation(
                "Deseja realmente excluir a viagem para "
                        + viagem.getDestino() + "?")) {

            viagemService.excluir(viagem.getId());

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
        dtpDataIda.setValue(null);
        dtpDataVolta.setValue(null);
        txtObservacoes.clear();
    }


    @FXML
    private void carregarViagem() {

        ArrayList<ViagemDTO> listaViagens = viagemService.listar();

        tblViagem.setItems(
                FXCollections.observableArrayList(listaViagens)
        );
    }


    @FXML
    private void initialize() {

        tblViagem.getSelectionModel().setCellSelectionEnabled(false);

        tblViagem.getSelectionModel().setSelectionMode(
                javafx.scene.control.SelectionMode.SINGLE
        );

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colDataIda.setCellValueFactory(new PropertyValueFactory<>("dataIda"));
        colDataVolta.setCellValueFactory(new PropertyValueFactory<>("dataVolta"));
        colObservacoes.setCellValueFactory(new PropertyValueFactory<>("observacoes"));

        // Impede que o usuário digite letras no campo de preço
        txtPreco.textProperty().addListener((obs, antigo, novo) -> {
            if (!novo.matches("\\d*(\\.\\d*)?")) {
                txtPreco.setText(antigo);
            }
        });

        // Atualiza os campos ao selecionar uma viagem
        tblViagem.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, viagem) -> {

                    if (viagem != null) {

                        txtDestino.setText(viagem.getDestino());
                        txtPreco.setText(String.valueOf(viagem.getPreco()));
                        txtObservacoes.setText(viagem.getObservacoes());

                        dtpDataIda.setValue(
                                new java.sql.Date(
                                        viagem.getDataIda().getTime()
                                ).toLocalDate()
                        );

                        dtpDataVolta.setValue(
                                new java.sql.Date(
                                        viagem.getDataVolta().getTime()
                                ).toLocalDate()
                        );
                    }
                }
        );

        carregarViagem();
    }


    private void mostrarMensagem(String texto, boolean isSucesso) {

        lblStatus.setText(texto);

        lblStatus.getStyleClass().removeAll(
                "status-erro",
                "status-sucesso"
        );

        if (isSucesso) {
            lblStatus.getStyleClass().add("status-sucesso");
        } else {
            lblStatus.getStyleClass().add("status-erro");
        }
    }


    @FXML
    private void carregarCampos() {

        ViagemDTO viagemSelecionada =
                tblViagem.getSelectionModel().getSelectedItem();

        if (viagemSelecionada != null) {

            txtDestino.setText(viagemSelecionada.getDestino());
            txtPreco.setText(String.valueOf(viagemSelecionada.getPreco()));
            txtObservacoes.setText(viagemSelecionada.getObservacoes());

            dtpDataIda.setValue(
                    new java.sql.Date(
                            viagemSelecionada.getDataIda().getTime()
                    ).toLocalDate()
            );

            dtpDataVolta.setValue(
                    new java.sql.Date(
                            viagemSelecionada.getDataVolta().getTime()
                    ).toLocalDate()
            );
        }
    }
}