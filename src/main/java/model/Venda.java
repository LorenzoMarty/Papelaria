package model;

import java.time.LocalDateTime;

public class Venda {

    private int id;
    private int usuarioId;
    private int produtoId;
    private int quantidade;
    private double precoUnitario;
    private LocalDateTime dataVenda;

    // campos extras para exibicao (preenchidos via JOIN)
    private String nomeUsuario;
    private String nomeProduto;

    public Venda() {
    }

    public Venda(int usuarioId, int produtoId, int quantidade, double precoUnitario) {
        this.usuarioId = usuarioId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public double getTotal() {
        return quantidade * precoUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(double precoUnitario) { this.precoUnitario = precoUnitario; }

    public LocalDateTime getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
}
