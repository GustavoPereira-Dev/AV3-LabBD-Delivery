USE AvaliacaoDeliveryAV3;

CREATE TABLE cliente (
    cpf VARCHAR(11) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(150),
    numero INT NOT NULL,
    cep VARCHAR(8),
    pontoreferencia VARCHAR(100),
    CONSTRAINT PK_Cliente PRIMARY KEY (cpf)
);

CREATE TABLE tipo (
    id INT IDENTITY(1,1) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    CONSTRAINT PK_Tipo PRIMARY KEY (id)
);

CREATE TABLE porcao (
    id INT IDENTITY(1,1) NOT NULL,
    tamanho VARCHAR(50) NOT NULL,
    CONSTRAINT PK_Porcao PRIMARY KEY (id)
);

CREATE TABLE ingrediente (
    id INT IDENTITY(1,1) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    formatoapresentacao VARCHAR(50),
    CONSTRAINT PK_Ingrediente PRIMARY KEY (id)
);

CREATE TABLE prato (
    id INT IDENTITY(1,1) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    id_tipo INT NOT NULL,
    CONSTRAINT PK_Prato PRIMARY KEY (id),
    CONSTRAINT FK_Prato_Tipo FOREIGN KEY (id_tipo) REFERENCES tipo(id)
);

CREATE TABLE prato_ingrediente (
    id_prato INT NOT NULL,
    id_ingrediente INT NOT NULL,
    id_porcao INT NOT NULL,
    CONSTRAINT PK_Prato_Ingrediente PRIMARY KEY (id_prato, id_ingrediente, id_porcao),
    CONSTRAINT FK_PI_Prato FOREIGN KEY (id_prato) REFERENCES prato(id),
    CONSTRAINT FK_PI_Ingrediente FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id),
    CONSTRAINT FK_PI_Porcao FOREIGN KEY (id_porcao) REFERENCES porcao(id)
);

CREATE TABLE pedido (
    id INT IDENTITY(1,1) NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    cpf_cliente VARCHAR(11) NOT NULL,
    id_prato INT NOT NULL,
    id_porcao INT NOT NULL,
    CONSTRAINT PK_Pedido PRIMARY KEY (id),
    CONSTRAINT FK_Pedido_Cliente FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf),
    CONSTRAINT FK_Pedido_Prato FOREIGN KEY (id_prato) REFERENCES prato(id),
    CONSTRAINT FK_Pedido_Porcao FOREIGN KEY (id_porcao) REFERENCES porcao(id)
);

GO

INSERT INTO cliente (cpf, nome, telefone, endereco, numero, cep, pontoreferencia) VALUES
('11122233344', 'João Silva', '11999990001', 'Rua das Flores', 123, '01001000', 'Perto da Padaria'),
('22233344455', 'Maria Oliveira', '11999990002', 'Av. Paulista', 1500, '01310000', 'Prédio Comercial'),
('33344455566', 'Carlos Souza', '11999990003', 'Rua Augusta', 500, '01305000', 'Ao lado do Banco'),
('44455566677', 'Ana Pereira', '11999990004', 'Rua da Consolação', 800, '01302000', NULL),
('55566677788', 'Roberto Santos', '11999990005', 'Alameda Santos', 200, '01418000', 'Esquina com Pamplona'),
('66677788899', 'Fernanda Lima', '11999990006', 'Rua Haddock Lobo', 300, '01414000', 'Em frente ao Hotel'),
('77788899900', 'Paulo Mendes', '11999990007', 'Rua Bela Cintra', 450, '01415000', NULL),
('88899900011', 'Juliana Costa', '11999990008', 'Rua Oscar Freire', 1000, '01426000', 'Loja de Roupas'),
('99900011122', 'Ricardo Alves', '11999990009', 'Rua Estados Unidos', 150, '01427000', 'Clube'),
('00011122233', 'Camila Rocha', '11999990010', 'Av. Rebouças', 2500, '05402000', 'Hospital'),
('12312312312', 'Lucas Martins', '11988887777', 'Rua Vergueiro', 90, '04101000', 'Metrô'),
('32132132132', 'Beatriz Nogueira', '11977776666', 'Rua Domingos de Morais', 45, '04110000', 'Shopping');

INSERT INTO tipo (nome) VALUES
('Prato Principal'),
('Lanche'),
('Sobremesa'),
('Bebida'),
('Entrada'),
('Salada');

INSERT INTO porcao (tamanho) VALUES
('Individual'),
('Pequena'),
('Média'),
('Grande'),
('Família');

INSERT INTO ingrediente (nome, formatoapresentacao) VALUES
('Arroz Branco', 'Cozido'),
('Feijão Carioca', 'Cozido'),
('Bife de Alcatra', 'Grelhado'),
('Batata Frita', 'Palito'),
('Alface Americana', 'Lavada/Folha'),
('Tomate', 'Rodelas'),
('Queijo Mussarela', 'Fatiado'),
('Pão de Hambúrguer', 'Cortado'),
('Hambúrguer Bovino', 'Grelhado 150g'),
('Macarrão Espaguete', 'Cozido'),
('Molho de Tomate', 'Líquido'),
('Frango Grelhado', 'Filé'),
('Bacon', 'Cubos Fritos'),
('Ovo', 'Frito'),
('Cebola', 'Caramelizada');

INSERT INTO prato (nome, id_tipo) VALUES
('Prato Feito (PF) Clássico', 1),
('X-Salada', 2),
('Macarronada à Bolonhesa', 1),
('X-Bacon Especial', 2),
('Salada Caesar', 6),
('Bife a Cavalo', 1),
('Frango Grelhado com Legumes', 1),
('Misto Quente', 2),
('Batata Frita Especial', 5),
('Omelete Completo', 1);

INSERT INTO prato_ingrediente (id_prato, id_ingrediente, id_porcao) VALUES
(1, 1, 1),
(1, 2, 1),
(1, 3, 1),
(1, 4, 1); 

INSERT INTO prato_ingrediente (id_prato, id_ingrediente, id_porcao) VALUES
(2, 8, 1),
(2, 9, 1),
(2, 7, 1),
(2, 5, 1),
(2, 6, 1);

INSERT INTO prato_ingrediente (id_prato, id_ingrediente, id_porcao) VALUES
(3, 10, 4),
(3, 11, 4),
(3, 7, 4),
(3, 10, 3),
(3, 11, 3);

INSERT INTO prato_ingrediente (id_prato, id_ingrediente, id_porcao) VALUES
(4, 8, 1),
(4, 9, 1),
(4, 7, 1),
(4, 13, 1);

INSERT INTO prato_ingrediente (id_prato, id_ingrediente, id_porcao) VALUES
(6, 3, 1),
(6, 14, 1),
(6, 1, 1);

INSERT INTO prato_ingrediente (id_prato, id_ingrediente, id_porcao) VALUES
(9, 4, 5),
(9, 13, 5),
(9, 7, 5);

INSERT INTO pedido (valor, cpf_cliente, id_prato, id_porcao) VALUES
(25.00, '11122233344', 1, 1),
(18.50, '22233344455', 2, 1),
(35.00, '33344455566', 3, 4),
(22.00, '44455566677', 4, 1),
(45.00, '55566677788', 9, 5),
(28.00, '66677788899', 6, 1),
(20.00, '77788899900', 2, 1),
(25.00, '88899900011', 1, 1),
(15.00, '99900011122', 8, 1),
(30.00, '00011122233', 7, 1),
(26.00, '12312312312', 3, 3),
(19.00, '32132132132', 10, 1),
(55.00, '11122233344', 9, 5),
(22.00, '22233344455', 4, 1),
(25.00, '33344455566', 1, 1);