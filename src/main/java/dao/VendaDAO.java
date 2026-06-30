package dao;

import model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    private Venda map(ResultSet rs) throws SQLException {
        Venda v = new Venda();
        v.setId(rs.getInt("id"));
        v.setUsuarioId(rs.getInt("usuario_id"));
        v.setProdutoId(rs.getInt("produto_id"));
        v.setQuantidade(rs.getInt("quantidade"));
        v.setPrecoUnitario(rs.getDouble("preco_unitario"));
        Timestamp ts = rs.getTimestamp("data_venda");
        if (ts != null) {
            v.setDataVenda(ts.toLocalDateTime());
        }
        try { v.setNomeUsuario(rs.getString("nome_usuario")); } catch (SQLException ignored) {}
        try { v.setNomeProduto(rs.getString("nome_produto")); } catch (SQLException ignored) {}
        return v;
    }

    public boolean inserir(Venda v) {
        String sql = "INSERT INTO venda (usuario_id, produto_id, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, v.getUsuarioId());
            stmt.setInt(2, v.getProdutoId());
            stmt.setInt(3, v.getQuantidade());
            stmt.setDouble(4, v.getPrecoUnitario());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar venda", e);
        }
    }

    public List<Venda> listar() {
        String sql = "SELECT v.*, u.nome AS nome_usuario, p.nome AS nome_produto " +
                     "FROM venda v " +
                     "JOIN usuario u ON v.usuario_id = u.id " +
                     "JOIN produto p ON v.produto_id = p.id " +
                     "ORDER BY v.data_venda DESC";

        List<Venda> lista = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar vendas", e);
        }

        return lista;
    }

    public double totalFaturado() {
        String sql = "SELECT COALESCE(SUM(quantidade * preco_unitario), 0) FROM venda";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao calcular total faturado", e);
        }

        return 0;
    }

    public long totalVendas() {
        String sql = "SELECT COUNT(*) FROM venda";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar vendas", e);
        }

        return 0;
    }
}
