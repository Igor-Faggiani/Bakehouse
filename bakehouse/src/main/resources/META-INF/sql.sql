-- Tabela para armazenar as categorias dos produtos
CREATE TABLE categories
(
    category_id SERIAL PRIMARY KEY,
    name        VARCHAR(100) UNIQUE NOT NULL
);

-- Tabela para armazenar os dados dos clientes
CREATE TABLE customers
(
    customer_id  SERIAL PRIMARY KEY,
    name         VARCHAR(255)       NOT NULL,
    cpf          VARCHAR(11) UNIQUE NOT NULL,
    phone        VARCHAR(15),
    total_points INTEGER            NOT NULL DEFAULT 0
);


-- Tabela de produtos, com referência à categoria
CREATE TABLE products
(
    product_id     SERIAL PRIMARY KEY,
    name           VARCHAR(255)   NOT NULL,
    price          DECIMAL(10, 2) NOT NULL,
    stock_quantity INTEGER        NOT NULL DEFAULT 0,
    redeemable     BOOLEAN                 DEFAULT FALSE,
    points_cost    INTEGER,
    image_url      VARCHAR(500),
    category_id    INTEGER        NOT NULL REFERENCES categories (category_id)
);

-- Tabela para registrar as vendas, com referência ao cliente
CREATE TABLE sales
(
    sale_id       SERIAL PRIMARY KEY,
    total_amount  DECIMAL(10, 2) NOT NULL,
    points_earned INTEGER        NOT NULL DEFAULT 0,
    sale_date     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id   INTEGER REFERENCES customers (customer_id)
);

-- Tabela para os itens de cada venda (tabela de junção)
CREATE TABLE sale_items
(
    sale_item_id SERIAL PRIMARY KEY,
    quantity     INTEGER        NOT NULL,
    unit_price   DECIMAL(10, 2) NOT NULL,
    sale_id      INTEGER        NOT NULL REFERENCES sales (sale_id),
    product_id   INTEGER        NOT NULL REFERENCES products (product_id)
);

-- Tabela para registrar a troca de pontos por produtos
CREATE TABLE point_redemptions
(
    redemption_id   SERIAL PRIMARY KEY,
    points_used     INTEGER   NOT NULL,
    redemption_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id     INTEGER   NOT NULL REFERENCES customers (customer_id),
    product_id      INTEGER   NOT NULL REFERENCES products (product_id)
);


-- Inserts
INSERT INTO categories (name)
VALUES ('Pães'),
       ('Doces'),
       ('Bebidas'),
       ('Salgados');

INSERT INTO customers (name, cpf, phone, total_points)
VALUES ('Ana Silva', '11122233344', '47999887766', 150),
       ('Bruno Costa', '55566677788', '47988776655', 75),
       ('Carla Dias', '99988877766', '47977665544', 0);

INSERT INTO products (name, price, stock_quantity, redeemable, points_cost, image_url, category_id)
VALUES ('Pão Francês', 0.75, 200, FALSE, NULL, '/images/pao_frances.jpg', 1),
       ('Sonho de Creme', 5.50, 30, TRUE, 50, '/images/sonho.jpg', 2),
       ('Coca-Cola 600ml', 8.00, 50, TRUE, 70, '/images/coca600.jpg', 3),
       ('Coxinha de Frango', 6.00, 40, FALSE, NULL, '/images/coxinha.jpg', 4),
       ('Baguete', 4.50, 25, FALSE, NULL, '/images/baguete.jpg', 1);