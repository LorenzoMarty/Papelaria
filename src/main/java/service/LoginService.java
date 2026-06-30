package service;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginService {

    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario autenticar(String email, String senha) {
        return dao.autenticar(email, senha);
    }
}
