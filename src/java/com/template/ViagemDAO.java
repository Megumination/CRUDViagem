package model.dao;

import model.dto.ViagemDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViagemDAO {

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
            System.out.println(">> Erro ao cadastrar: " + e.getMessage());
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
            System.out.println(">> Erro ao alterar: " + e.getMessage());
        }
    }

    public List<ViagemDTO> listarTodos() {
        String sql = "SELECT * FROM viagem";
        List<ViagemDTO> lista = new ArrayList<>();

        try (Connection conn = new Conexao().obterConexao(); //Abre a conexão com o banco de dados
             PreparedStatement ps = conn.prepareStatement(sql); //Prepara o comando SQL para ser enviado
             ResultSet rs = ps.executeQuery()) { //Executa a consulta e guarda os resultados vindos do banco

            while (rs.next()) {
                ViagemDTO v = new ViagemDTO();
                v.setId(rs.getInt("id"));
                v.setDestino(rs.getString("destino"));
                v.setDataIda(rs.getDate("data_ida"));
                v.setDataVolta(rs.getDate("data_volta"));
                v.setPreco(rs.getDouble("preco"));
                v.setObservacoes(rs.getString("observacoes"));
                lista.add(v);    //coloca o objeto na lista
            }
        } catch (SQLException e) {
            System.out.println(">> Erro ao listar: " + e.getMessage());
        }
        return lista;   //devolve a lista com todas as viagens encontradas
    }

    public void excluir(int id) {
        String sql = "DELETE FROM viagem WHERE id = ?";
        try (Connection conn = new Conexao().obterConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);  //define o valor do parametro pelo id
            ps.executeUpdate(); //executa o comando q altera a tabela
            System.out.println(">> Sucesso: Viagem excluida!");

        } catch (SQLException e) {
            System.out.println(">> Erro ao excluir: " + e.getMessage());
        }
    }
}