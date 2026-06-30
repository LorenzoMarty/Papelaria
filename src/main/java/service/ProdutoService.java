package service;

import dao.ProdutoDAO;
import model.Produto;

import java.util.List;

public class ProdutoService {

    private final ProdutoDAO dao = new ProdutoDAO();

    public List<Produto> listar() {
        return dao.listar();
    }

    public List<Produto> listarComEstoqueBaixo(int limite) {
        return dao.listarComEstoqueBaixo(limite);
    }

    public boolean inserir(Produto p) {
        return dao.inserir(p);
    }

    public void excluir(int id) {
        dao.excluir(id);
    }

    public void atualizar(Produto p) {
        dao.atualizar(p);
    }

    public Produto buscarPorId(int id) {
        return dao.buscarPorId(id);
    }
}
