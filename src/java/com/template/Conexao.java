package com.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private final String url = "jdbc:postgresql://localhost:5432/Viagem";
    private final String usuario = "postgres";
    private final String senha = "postgres";

    public Connection obterConexao() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver nao encontrado: " + e.getMessage());
        }
    }
}