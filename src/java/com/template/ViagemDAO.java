package com.template;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViagemDAO {

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

    public ArrayList<ViagemDTO> listarViagem() {

        String sql = "SELECT * FROM viagem";
        ArrayList<ViagemDTO> lista = new ArrayList<>();

        try (Connection conn = new Conexao().obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ViagemDTO viagem = new ViagemDTO();

                viagem.setId(rs.getInt("id"));
                viagem.setDestino(rs.getString("destino"));
                viagem.setDataIda(rs.getDate("data_ida"));
                viagem.setDataVolta(rs.getDate("data_volta"));
                viagem.setPreco(rs.getDouble("preco"));
                viagem.setObservacoes(rs.getString("observacoes"));

                lista.add(viagem);
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

            System.out.println(">> Sucesso: Viagem excluída!");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Erro ao excluir viagem", e);
        }
    }
}