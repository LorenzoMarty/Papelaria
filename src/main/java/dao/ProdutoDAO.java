package dao;

import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private Produto map(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setDescricao(rs.getString("descricao"));
        p.setPreco(rs.getDouble("preco"));
        p.setQuantidade(rs.getInt("quantidade"));
        return p;
    }

    public boolean inserir(Produto p) {
        String sql = "INSERT INTO produto (nome, descricao, preco, quantidade) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getPreco());
            stmt.setInt(4, p.getQuantidade());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir item", e);
        }
    }

    public List<Produto> listar() {
        String sql = "SELECT * FROM produto ORDER BY id";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens", e);
        }

        return lista;
    }

    public List<Produto> listarComEstoqueBaixo(int limite) {
        String sql = "SELECT * FROM produto WHERE quantidade <= ? ORDER BY quantidade ASC";
        List<Produto> lista = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limite);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar itens com estoque baixo", e);
        }

        return lista;
    }

    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar item", e);
        }

        return null;
    }

    public void atualizar(Produto p) {
        String sql = "UPDATE produto SET nome=?, descricao=?, preco=?, quantidade=? WHERE id=?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(3, p.getPreco());
            stmt.setInt(4, p.getQuantidade());
            stmt.setInt(5, p.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar item", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir item", e);
        }
    }
}
