package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {
    private static final String URL = "jdbc:sqlite:banco.db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public static void createTables() {
        String sqlConta = "CREATE TABLE IF NOT EXISTS contas (" +
                "  numero INTEGER PRIMARY KEY," +
                "  saldo REAL NOT NULL," +
                "  limite REAL NOT NULL" +
                ");";

        String sqlMovimentacao = "CREATE TABLE IF NOT EXISTS movimentacoes (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  data TEXT NOT NULL," +
                "  valor REAL NOT NULL," +
                "  tipo TEXT NOT NULL," +
                "  conta_numero INTEGER NOT NULL," +
                "  FOREIGN KEY(conta_numero) REFERENCES contas(numero)" +
                ");";

        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlConta);
            stmt.execute(sqlMovimentacao);
            System.out.println("Banco de dados pronto.");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}