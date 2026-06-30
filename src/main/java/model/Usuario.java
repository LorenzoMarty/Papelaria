package model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private boolean ativo;
    private boolean admin;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, boolean ativo, boolean admin) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.admin = admin;
    }

    public Usuario(int id, String nome, String email, String senha, boolean ativo, boolean admin) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ativo = ativo;
        this.admin = admin;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    public boolean isAdmin() {
        return admin;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
