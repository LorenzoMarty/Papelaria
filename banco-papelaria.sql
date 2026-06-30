-- Sistema Papelaria - Script de criacao do banco de dados
-- 1. Crie o banco: CREATE DATABASE papelaria;
-- 2. Conecte em papelaria e execute este script.

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    senha VARCHAR(120) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT true,
    admin BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    preco NUMERIC(10, 2) NOT NULL,
    quantidade INTEGER NOT NULL
);

-- Tabela de vendas com chaves estrangeiras para usuario e produto
CREATE TABLE venda (
    id SERIAL PRIMARY KEY,
    usuario_id INTEGER NOT NULL REFERENCES usuario(id),
    produto_id INTEGER NOT NULL REFERENCES produto(id),
    quantidade INTEGER NOT NULL,
    preco_unitario NUMERIC(10, 2) NOT NULL,
    data_venda TIMESTAMP NOT NULL DEFAULT now()
);

-- Dados iniciais: usuarios
INSERT INTO usuario (nome, email, senha, ativo, admin)
VALUES ('Administrador', 'admin@papelaria.com', 'admin123', true, true);

INSERT INTO usuario (nome, email, senha, ativo, admin)
VALUES ('Roberto', 'roberto@papelaria.com', 'user123', true, false);

-- Dados iniciais: usuarios extras
INSERT INTO usuario (nome, email, senha, ativo, admin)
VALUES ('Ana Lima', 'ana@papelaria.com', 'ana123', true, false);

INSERT INTO usuario (nome, email, senha, ativo, admin)
VALUES ('Carlos Souza', 'carlos@papelaria.com', 'carlos123', true, false);

-- Dados iniciais: produtos
INSERT INTO produto (nome, descricao, preco, quantidade) VALUES
('Caderno universitario', 'Caderno 10 materias', 24.90, 30),
('Caneta azul', 'Caneta esferografica azul', 2.50, 100),
('Lapis preto', 'Lapis grafite HB', 1.80, 80),
('Borracha branca', 'Borracha para lapis e caneta', 1.20, 60),
('Regua 30cm', 'Regua plastica transparente', 3.50, 45),
('Apontador', 'Apontador com deposito', 2.00, 70),
('Marca-texto amarelo', 'Caneta marca-texto fluorescente', 4.90, 40),
('Pasta AZ', 'Pasta fichario grande lombada', 18.90, 15),
('Post-it', 'Bloco de notas adesivas 100 folhas', 8.50, 25),
('Fita adesiva', 'Fita transparente 45mm x 45m', 5.20, 3);

-- Vendas demo (referencia: usuario 1 = admin, produtos 1-10)
INSERT INTO venda (usuario_id, produto_id, quantidade, preco_unitario, data_venda) VALUES
(1, 1, 3, 24.90, now() - interval '10 days'),
(1, 2, 10, 2.50, now() - interval '9 days'),
(1, 3, 5, 1.80, now() - interval '8 days'),
(1, 6, 8, 2.00, now() - interval '7 days'),
(1, 5, 4, 3.50, now() - interval '6 days'),
(1, 7, 6, 4.90, now() - interval '5 days'),
(1, 8, 2, 18.90, now() - interval '4 days'),
(1, 9, 3, 8.50, now() - interval '3 days'),
(1, 4, 12, 1.20, now() - interval '2 days'),
(1, 2, 20, 2.50, now() - interval '1 day'),
(1, 1, 2, 24.90, now() - interval '12 hours'),
(1, 10, 1, 5.20, now());
