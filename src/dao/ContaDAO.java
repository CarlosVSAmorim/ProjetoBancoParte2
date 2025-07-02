package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ContaCorrente;
import util.ConnectionFactory;

public class ContaDAO {

    public void adicionar(ContaCorrente conta) throws SQLException {
        String sql = "INSERT INTO contas(numero, saldo, limite) VALUES(?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, conta.getNumero());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setDouble(3, conta.getLimite());
            stmt.executeUpdate();
        }
    }

    public void atualizar(ContaCorrente conta) throws SQLException {
        String sql = "UPDATE contas SET saldo = ?, limite = ? WHERE numero = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, conta.getSaldo());
            stmt.setDouble(2, conta.getLimite());
            stmt.setInt(3, conta.getNumero());
            stmt.executeUpdate();
        }
    }

    public void remover(int numero) throws SQLException {
        String sqlMov = "DELETE FROM movimentacoes WHERE conta_numero = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlMov)) {
            stmt.setInt(1, numero);
            stmt.executeUpdate();
        }

        String sqlConta = "DELETE FROM contas WHERE numero = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sqlConta)) {
            stmt.setInt(1, numero);
            stmt.executeUpdate();
        }
    }

    public ContaCorrente buscarPorNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM contas WHERE numero = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ContaCorrente(
                            rs.getInt("numero"),
                            rs.getDouble("saldo"),
                            rs.getDouble("limite")
                    );
                }
            }
        }
        return null;
    }

    public boolean existe(int numero) throws SQLException {
        return buscarPorNumero(numero) != null;
    }

    public List<ContaCorrente> listarTodas() throws SQLException {
        List<ContaCorrente> contas = new ArrayList<>();
        String sql = "SELECT * FROM contas ORDER BY numero";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contas.add(new ContaCorrente(
                        rs.getInt("numero"),
                        rs.getDouble("saldo"),
                        rs.getDouble("limite")
                ));
            }
        }
        return contas;
    }
}