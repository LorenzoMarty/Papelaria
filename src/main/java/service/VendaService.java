package service;

import dao.ProdutoDAO;
import dao.VendaDAO;
import model.Produto;
import model.Venda;

import java.util.List;

public class VendaService {

    private final VendaDAO vendaDAO = new VendaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public String registrarVenda(int produtoId, int usuarioId, int quantidade) {
        if (quantidade <= 0) {
            return "Quantidade deve ser maior que zero.";
        }

        Produto produto = produtoDAO.buscarPorId(produtoId);
        if (produto == null) {
            return "Produto não encontrado.";
        }
        if (produto.getQuantidade() < quantidade) {
            return "Estoque insuficiente. Disponível: " + produto.getQuantidade() + " unidade(s).";
        }

        Venda venda = new Venda(usuarioId, produtoId, quantidade, produto.getPreco());
        vendaDAO.inserir(venda);

        produto.setQuantidade(produto.getQuantidade() - quantidade);
        produtoDAO.atualizar(produto);

        return null;
    }

    public List<Venda> listar() {
        return vendaDAO.listar();
    }

    public double totalFaturado() {
        return vendaDAO.totalFaturado();
    }

    public long totalVendas() {
        return vendaDAO.totalVendas();
    }
}
