package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Movimentacao;
import model.TipoTransacao;
import util.ConnectionFactory;

public class MovimentacaoDAO {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public void adicionar(Movimentacao mov, int contaNumero) throws SQLException {
        String sql = "INSERT INTO movimentacoes(data, valor, tipo, conta_numero) VALUES(?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mov.getData().format(FORMATTER));
            stmt.setDouble(2, mov.getValor());
            stmt.setString(3, mov.getTipo().name());
            stmt.setInt(4, contaNumero);
            stmt.executeUpdate();
        }
    }

    public List<Movimentacao> listarPorConta(int contaNumero) throws SQLException {
        List<Movimentacao> movimentacoes = new ArrayList<>();
        String sql = "SELECT * FROM movimentacoes WHERE conta_numero = ? ORDER BY data DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, contaNumero);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDateTime data = LocalDateTime.parse(rs.getString("data"), FORMATTER);
                    double valor = rs.getDouble("valor");
                    TipoTransacao tipo = TipoTransacao.valueOf(rs.getString("tipo"));
                    movimentacoes.add(new Movimentacao(data, valor, tipo));
                }
            }
        }
        return movimentacoes;
    }
}