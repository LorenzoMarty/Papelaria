package dao;

import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Usuario map(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getString("senha"));
        u.setAtivo(rs.getBoolean("ativo"));
        u.setAdmin(rs.getBoolean("admin"));
        return u;
    }

    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ? AND ativo = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);
            stmt.setBoolean(3, true);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autenticar usuario", e);
        }

        return null;
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar e-mail", e);
        }

        return false;
    }

    public boolean inserir(Usuario u) {
        String sql = "INSERT INTO usuario (nome, email, senha, ativo, admin) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setBoolean(4, u.isAtivo());
            stmt.setBoolean(5, u.isAdmin());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir usuario", e);
        }
    }

    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuario ORDER BY id";
        List<Usuario> lista = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(map(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar usuarios", e);
        }

        return lista;
    }

    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuario", e);
        }

        return null;
    }

    public void atualizar(Usuario u) {
        String sql = "UPDATE usuario SET nome=?, email=?, senha=?, ativo=?, admin=? WHERE id=?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());
            stmt.setBoolean(4, u.isAtivo());
            stmt.setBoolean(5, u.isAdmin());
            stmt.setInt(6, u.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuario", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir usuario", e);
        }
    }
}
