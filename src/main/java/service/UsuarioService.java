package service;

import dao.UsuarioDAO;
import model.Usuario;

import java.util.List;

public class UsuarioService {

    private final UsuarioDAO dao = new UsuarioDAO();

    public boolean inserir(Usuario u) {
        return dao.inserir(u);
    }

    public boolean emailExiste(String email) {
        return dao.emailExiste(email);
    }

    public List<Usuario> listar() {
        return dao.listar();
    }

    public Usuario buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public void atualizar(Usuario u) {
        dao.atualizar(u);
    }

    public void excluir(int id) {
        dao.excluir(id);
    }
}
