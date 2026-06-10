package com.template;

import java.util.ArrayList;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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

        ViagemDTO viagem = new ViagemDTO();

        viagem.setDestino(txtDestino.getText());
        viagem.setPreco(Double.parseDouble(txtPreco.getText()));
        viagem.setDataIda(java.sql.Date.valueOf(dtpDataIda.getValue()));
        viagem.setDataVolta(java.sql.Date.valueOf(dtpDataVolta.getValue()));
        viagem.setObservacoes(txtObservacoes.getText());

        ViagemDAO dao = new ViagemDAO();
        dao.cadastrar(viagem);

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

            ViagemDAO dao = new ViagemDAO();
            dao.alterar(viagem);

            mostrarMensagem("Viagem alterada com sucesso!", true);

            carregarViagem();
            limparCampos();
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {

        ViagemDTO viagem = tblViagem.getSelectionModel().getSelectedItem();

        // Validação trocando o println pelo seu mostrarMensagem
        if (viagem == null) {
            mostrarMensagem("Selecione uma viagem para excluir!", false);
            return;
        }

        // Alerta de Confirmação
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmação");
        alerta.setHeaderText(null); // Deixa o alerta mais limpo
        alerta.setContentText("Deseja realmente excluir a viagem para " + viagem.getDestino() + "?");

        // executa só se o usuário clicar em OK
        if (alerta.showAndWait().get() == ButtonType.OK) {
            ViagemDAO dao = new ViagemDAO();
            dao.excluir(viagem.getId());

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

        ViagemDAO dao = new ViagemDAO();

        // Busca todas as viagens cadastradas no banco
        ArrayList<ViagemDTO> listaViagem = dao.listarViagem();

        // Converte a ArrayList para ObservableList para exibir na TableView
        tblViagem.setItems(
                FXCollections.observableArrayList(listaViagem)
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
    private void filtrarTabela() {
        String busca = txtPesquisa.getText().toLowerCase();
        // Aqui você filtra sua lista de viagens e dá um tblViagem.setItems() com o resultado
    }



    @FXML
    private void carregarCampos() {

        // Pega a linha selecionada na tabela
        ViagemDTO viagemDTO = tblViagem.getSelectionModel().getSelectedItem();

        if (viagemDTO != null) {

            txtDestino.setText(viagemDTO.getDestino());
            txtPreco.setText(String.valueOf(viagemDTO.getPreco()));
            txtObservacoes.setText(viagemDTO.getObservacoes());

            dtpDataIda.setValue(
                    new java.sql.Date(viagemDTO.getDataIda().getTime()).toLocalDate()
            );

            dtpDataVolta.setValue(
                    new java.sql.Date(viagemDTO.getDataVolta().getTime()).toLocalDate()
            );
        }
    }
}

