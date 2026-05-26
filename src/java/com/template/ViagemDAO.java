package com.template;

import model.dto.ViagemDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// imports do logger
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViagemDAO {

    // logger adicionado
    private static final Logger logger = Logger.getLogger(ViagemDAO.class.getName());

    public void cadastrar(ViagemDTO viagem) {
        String sql = "INSERT INTO viagem (destino, data_ida, data_volta, preco, observacoes) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = new Conexao().obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, viagem.getDestino());
            ps.setDate(2, new java.sql.Date(viagem.getDataIda().getTime()));
            ps.setDate(3, new java.sql.Date(viagem.getDataVolta().getTime()));
            ps.setDouble(4, viagem.getPreco());
            ps.setString(5, viagem.getObservacoes());

            ps.executeUpdate();
            System.out.println(">> Sucesso: Viagem cadastrada!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao cadastrar viagem", e);
        }
    }

    public void alterar(ViagemDTO viagem) {
        String sql = "UPDATE viagem SET destino = ?, data_ida = ?, data_volta = ?, preco = ?, observacoes = ? WHERE id = ?";

        try (Connection conn = new Conexao().obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, viagem.getDestino());
            ps.setDate(2, new java.sql.Date(viagem.getDataIda().getTime()));
            ps.setDate(3, new java.sql.Date(viagem.getDataVolta().getTime()));
            ps.setDouble(4, viagem.getPreco());
            ps.setString(5, viagem.getObservacoes());
            ps.setInt(6, viagem.getId());

            ps.executeUpdate();
            System.out.println(">> Sucesso: Viagem alterada!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao alterar viagem", e);
        }
    }

    public List<ViagemDTO> listarTodos() {
        String sql = "SELECT * FROM viagem";
        List<ViagemDTO> lista = new ArrayList<>();

        try (Connection conn = new Conexao().obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ViagemDTO v = new ViagemDTO();
                v.setId(rs.getInt("id"));
                v.setDestino(rs.getString("destino"));
                v.setDataIda(rs.getDate("data_ida"));
                v.setDataVolta(rs.getDate("data_volta"));
                v.setPreco(rs.getDouble("preco"));
                v.setObservacoes(rs.getString("observacoes"));
                lista.add(v);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao listar viagens", e);
        }

        return lista;
    }

    public void excluir(int id) {
        String sql = "DELETE FROM viagem WHERE id = ?";

        try (Connection conn = new Conexao().obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println(">> Sucesso: Viagem excluida!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir viagem", e);
        }
    }
}}