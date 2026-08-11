package com.template.service;

import java.util.ArrayList;

import com.template.model.dao.ViagemDAO;
import com.template.model.dto.ViagemDTO;

public class ViagemService {

    private ViagemDAO viagemDAO = new ViagemDAO();

    public void cadastrar(ViagemDTO viagem) {
        viagemDAO.cadastrar(viagem);
    }

    public void alterar(ViagemDTO viagem) {
        viagemDAO.alterar(viagem);
    }

    public void excluir(int id) {
        viagemDAO.excluir(id);
    }

    public ArrayList<ViagemDTO> listar() {
        return viagemDAO.listarViagem();
    }
}