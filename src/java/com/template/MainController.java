package com.template;


import java.util.ArrayList;
import java.util.Date;


import javafx.collections.FXCollections;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
    private TextField txtDestino;


    @FXML
    private TextField txtPreco;


    @FXML
    private TextField txtObservacoes;


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


        ViagemDTO viagem = new ViagemDTO();


        viagem.setDestino(txtDestino.getText());
        viagem.setPreco(Double.parseDouble(txtPreco.getText()));
        viagem.setDataIda(java.sql.Date.valueOf(dtpDataIda.getValue()));
        viagem.setDataVolta(java.sql.Date.valueOf(dtpDataVolta.getValue()));
        viagem.setObservacoes(txtObservacoes.getText());


        ViagemDAO dao = new ViagemDAO();
        dao.cadastrar(viagem);


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


            carregarViagem();
            limparCampos();
        }
    }


    @FXML
    private void btnExcluirAction(ActionEvent event) {


        ViagemDTO viagem = tblViagem.getSelectionModel().getSelectedItem();


        if (viagem == null) {
            System.out.println("Selecione uma viagem para excluir!");
            return;
        }


        ViagemDAO dao = new ViagemDAO();
        dao.excluir(viagem.getId());


        carregarViagem();
        limparCampos();
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
    private void selecionarViagem() {


        ViagemDTO viagem = tblViagem.getSelectionModel().getSelectedItem();


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


    @FXML
    private void carregarViagem() {


        ViagemDAO dao = new ViagemDAO();


        ArrayList<ViagemDTO> listaViagem = dao.listarViagem();


        tblViagem.setItems(
                FXCollections.observableArrayList(listaViagem)
        );
    }


    @FXML
    private void initialize() {
        tblViagem.getSelectionModel().setCellSelectionEnabled(false);
        tblViagem.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("destino"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colDataIda.setCellValueFactory(new PropertyValueFactory<>("dataIda"));
        colDataVolta.setCellValueFactory(new PropertyValueFactory<>("dataVolta"));
        colObservacoes.setCellValueFactory(new PropertyValueFactory<>("observacoes"));


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

