CREATE TABLE IF NOT EXISTS cliente(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS produto(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(100),
    preco_unitario NUMERIC(20, 2)
);

CREATE TABLE IF NOT EXISTS pedido(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    cliente_id INTEGER REFERENCES cliente(id),
    data_pedido TIMESTAMP,
    total NUMERIC(20, 2)
);

CREATE TABLE IF NOT EXISTS item_pedido(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    pedido_id INTEGER REFERENCES pedido(id),
    produto_id INTEGER REFERENCES produto(id),
    quantidade INTEGER
);