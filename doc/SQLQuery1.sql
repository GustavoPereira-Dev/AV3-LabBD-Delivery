CREATE DATABASE DeliveryAV3;

USE DeliveryAV3

CREATE TABLE cliente (
    cpf VARCHAR(11) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    telefone VARCHAR(11) NOT NULL,
    endereco VARCHAR(100) NOT NULL,
    numero INT NOT NULL,
    cep VARCHAR(8) NOT NULL,
    pontoreferencia VARCHAR(100),
    CONSTRAINT PK_Cliente PRIMARY KEY (cpf)
);
GO
CREATE TABLE tipo (
    id INT IDENTITY(1,1) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    CONSTRAINT PK_Tipo PRIMARY KEY (id)
);
GO
CREATE TABLE porcao (
    id INT IDENTITY(1,1) NOT NULL,
    tamanho VARCHAR(50) NOT NULL,
    valor DECIMAL(7,2)	NOT NULL,
    CONSTRAINT PK_Porcao PRIMARY KEY (id)
);
GO
CREATE TABLE ingrediente (
    id INT IDENTITY(1,1) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    formatoapresentacao VARCHAR(50) NOT NULL,
    CONSTRAINT PK_Ingrediente PRIMARY KEY (id)
);
GO
CREATE TABLE prato (
    id VARCHAR(10) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    valor DECIMAL(7,2)	NOT NULL,
    id_tipo INT NOT NULL,
    CONSTRAINT PK_Prato PRIMARY KEY (id),
    CONSTRAINT FK_Prato_Tipo FOREIGN KEY (id_tipo) REFERENCES tipo(id)
);
GO
CREATE TABLE prato_ingrediente_porcao (
    id_prato VARCHAR(10) NOT NULL,
    id_ingrediente INT NOT NULL,
    id_porcao INT
    CONSTRAINT PK_Prato_Ingrediente PRIMARY KEY (id_prato, id_ingrediente, id_porcao),
    CONSTRAINT FK_PI_Prato FOREIGN KEY (id_prato) REFERENCES prato(id),
    CONSTRAINT FK_PI_Ingrediente FOREIGN KEY (id_ingrediente) REFERENCES ingrediente(id),
    CONSTRAINT FK_PI_Porcao FOREIGN KEY (id_porcao) REFERENCES porcao(id)
);
GO
CREATE TABLE pedido (
    id INT IDENTITY(1,1) NOT NULL,
    valor DECIMAL(7, 2) NOT NULL,
    cpf_cliente VARCHAR(11) NOT NULL,
    id_prato VARCHAR(10),
    id_porcao INT NOT NULL,
    data DATE NOT NULL
    CONSTRAINT PK_Pedido PRIMARY KEY (id),
    CONSTRAINT FK_Pedido_Cliente FOREIGN KEY (cpf_cliente) REFERENCES cliente(cpf),
    CONSTRAINT FK_Pedido_Prato FOREIGN KEY (id_prato) REFERENCES prato(id),
    CONSTRAINT FK_Pedido_Porcao FOREIGN KEY (id_porcao) REFERENCES porcao(id)
);

INSERT INTO Cliente (cpf, nome, telefone, endereco, numero, cep, pontoReferencia) VALUES
('11122233344', 'Jo�o Silva', '11999990001', 'Rua das Flores', 123, '01001000', 'Perto da Padaria'),
('22233344455', 'Maria Oliveira', '11999990002', 'Av. Paulista', 1500, '01310000', 'Pr�dio Comercial'),
('33344455566', 'Carlos Souza', '11999990003', 'Rua Augusta', 500, '01305000', 'Ao lado do Banco'),
('44455566677', 'Ana Pereira', '11999990004', 'Rua da Consola��o', 800, '01302000', NULL),
('55566677788', 'Roberto Santos', '11999990005', 'Alameda Santos', 200, '01418000', 'Esquina com Pamplona'),
('66677788899', 'Fernanda Lima', '11999990006', 'Rua Haddock Lobo', 300, '01414000', 'Em frente ao Hotel'),
('77788899900', 'Paulo Mendes', '11999990007', 'Rua Bela Cintra', 450, '01415000', NULL),
('88899900011', 'Juliana Costa', '11999990008', 'Rua Oscar Freire', 1000, '01426000', 'Loja de Roupas'),
('99900011122', 'Ricardo Alves', '11999990009', 'Rua Estados Unidos', 150, '01427000', 'Clube'),
('00011122233', 'Camila Rocha', '11999990010', 'Av. Rebou�as', 2500, '05402000', 'Hospital'),
('12312312312', 'Lucas Martins', '11988887777', 'Rua Vergueiro', 90, '04101000', 'Metr�'),
('32132132132', 'Beatriz Nogueira', '11977776666', 'Rua Domingos de Morais', 45, '04110000', 'Shopping')
('11111111101', 'Mariana Ximenes', '11912345678', 'Rua das Acácias', 101, '02020000', 'Próximo ao Parque'),
('22222222202', 'Bruno Gagliasso', '11923456789', 'Av. Ibirapuera', 2020, '04020000', 'Shopping'),
('33333333303', 'Giovanna Antonelli', '11934567890', 'Rua Oscar Freire', 303, '01426000', 'Boutique A'),
('44444444404', 'Lázaro Ramos', '11945678901', 'Rua da Mooca', 404, '03104000', 'Pizzaria'),
('55555555505', 'Taís Araújo', '11956789012', 'Av. Brasil', 505, '01430000', 'Jardins'),
('66666666606', 'Wagner Moura', '11967890123', 'Rua Augusta', 606, '01305000', 'Teatro'),
('77777777707', 'Adriana Esteves', '11978901234', 'Rua Tuiuti', 707, '03307000', 'Tatuapé'),
('88888888808', 'Vladimir Brichta', '11989012345', 'Rua Cerro Corá', 808, '05061000', 'Lapa'),
('99999999909', 'Cláudia Raia', '11990123456', 'Av. Morumbi', 909, '05606000', 'Estádio'),
('10101010110', 'Edson Celulari', '11901234567', 'Rua Domingos de Morais', 1010, '04010000', 'Vila Mariana'),
('12121212112', 'Paolla Oliveira', '11912341234', 'Rua Palestra Itália', 1212, '05005000', 'Allianz Parque'),
('13131313113', 'Rodrigo Lombardi', '11923452345', 'Av. Engenheiro Caetano', 1313, '02523000', 'Marginal'),
('14141414114', 'Juliana Paes', '11934563456', 'Rua Voluntários da Pátria', 1414, '02011000', 'Santana'),
('15151515115', 'Reynaldo Gianecchini', '11945674567', 'Rua Teodoro Sampaio', 1515, '05405000', 'Pinheiros'),
('16161616116', 'Grazi Massafera', '11956785678', 'Av. Santo Amaro', 1616, '04505000', 'Brooklin');

GO
INSERT INTO Tipo VALUES
('Prato Principal'),
('Lanche'),
('Sobremesa'),
('Bebida'),
('Entrada'),
('Salada'),
('Vegano'),
('Japonês'),
('Árabe'),
('Mexicano');
GO
INSERT INTO Porcao VALUES
('Pequena', 0.0),
('M�dia', 1.5),
('Grande', 2.0)

GO
INSERT INTO Ingrediente VALUES
('Arroz Branco', 'Cozido'),
('Feij�o Carioca', 'Cozido'),
('Bife de Alcatra', 'Grelhado'),
('Batata Frita', 'Palito'),
('Alface Americana', 'Lavada/Folha'),
('Tomate', 'Rodelas'),
('Queijo Mussarela', 'Fatiado'),
('P�o de Hamb�rguer', 'Cortado'),
('Hamb�rguer Bovino', 'Grelhado 150g'),
('Macarr�o Espaguete', 'Cozido'),
('Molho de Tomate', 'L�quido'),
('Frango Grelhado', 'Fil�'),
('Bacon', 'Cubos Fritos'),
('Ovo', 'Frito'),
('Cebola', 'Caramelizada'),
('Salmão', 'Filé Cru/Cozido'),
('Arroz Shari', 'Temperado'),
('Alga Nori', 'Folha'),
('Cream Cheese', 'Pasta'),
('Pepino', 'Tiras'),
('Grão de Bico', 'Cozido/Pasta'),
('Tahine', 'Pasta'),
('Pão Sírio', 'Inteiro'),
('Kibe', 'Assado'),
('Carne Moída', 'Refogada'),
('Tortilla', 'Disco de Milho'),
('Guacamole', 'Pasta'),
('Pimenta Jalapeño', 'Fatiada'),
('Cheddar', 'Líquido'),
('Tofu', 'Cubos'),
('Cogumelo Shitake', 'Fatiado'),
('Brócolis', 'Cozido'),
('Cenoura', 'Ralada'),
('Abobrinha', 'Grelhada'),
('Berinjela', 'Assada');

GO
INSERT INTO Prato VALUES
('P0001', 'Prato Feito (PF) Cl�ssico', 24.0, 1),
('P0002', 'X-Salada', 15.0, 2),
('P0003', 'Macarronada � Bolonhesa', 22.0, 1),
('P0004', 'X-Bacon Especial', 20.0, 2),
('P0005', 'Salada Caesar', 18.0, 6),
('P0006', 'Bife a Cavalo', 36.0, 1),
('P0007', 'Frango Grelhado com Legumes', 28.0, 1),
('P0008', 'Misto Quente', 10.0, 2),
('P0009', 'Batata Frita Especial', 15.0, 5),
('P0010', 'Omelete Completo', 16.0, 1),
('PA7X92B1', 'Combinado Sushi Simples', 45.00, 7),
('PB829LKS', 'Temaki Salmão', 25.00, 7),
('PC92KD82', 'Yakisoba Misto', 35.00, 7),
('PD102938', 'Falafel no Prato', 30.00, 8),
('PE918273', 'Kibe Assado Recheado', 22.00, 8),
('PF564738', 'Tabule Fresco', 20.00, 8),
('PG928374', 'Burrito de Carne', 32.00, 9),
('PH192837', 'Nachos Supreme', 40.00, 9),
('PI564738', 'Tacos de Frango', 28.00, 9),
('PJ918273', 'Moqueca Vegana', 38.00, 6),
('PK192837', 'Strogonoff de Cogumelos', 35.00, 6),
('PL564738', 'Hamburguer de Grão de Bico', 26.00, 6),
('PM918273', 'Escondidinho de Legumes', 30.00, 6),
('PN192837', 'Salada de Frutas', 15.00, 5),
('PO564738', 'Bolo de Cenoura Vegan', 18.00, 5);
SELECT * FROM Prato
DELETE FROM Prato
GO

INSERT INTO Prato_Ingrediente_Porcao (id_prato, id_ingrediente, id_porcao) VALUES
('P0001', 1, 1),
('P0001', 2, 1),
('P0001', 3, 1),
('P0001', 4, 1)
GO
INSERT INTO Prato_Ingrediente_Porcao (id_prato, id_ingrediente, id_porcao) VALUES
('P0002', 8, 1),
('P0002', 9, 1),
('P0002', 7, 1),
('P0002', 5, 1),
('P0002', 6, 1)
GO
INSERT INTO Prato_Ingrediente_Porcao (id_prato, id_ingrediente, id_porcao) VALUES
('P0004', 8, 1),
('P0004', 9, 1),
('P0004', 7, 1),
('P0004', 13, 1)
GO
INSERT INTO Prato_Ingrediente_Porcao (id_prato, id_ingrediente, id_porcao) VALUES
('P0006', 3, 1),
('P0006', 14, 1),
('P0006', 1, 1)
GO
INSERT INTO Prato_Ingrediente_Porcao (id_prato, id_ingrediente, id_porcao) VALUES
('P0009', 4, 3),
('P0009', 13, 3),
('P0009', 7, 3)

INSERT INTO prato_ingrediente_porcao (id_prato, id_ingrediente, id_porcao) VALUES 
('PA7X92B1', 16, 2),
('PA7X92B1', 17, 2),
('PA7X92B1', 18, 2);

INSERT INTO prato_ingrediente_porcao (id_prato, id_ingrediente, id_porcao) VALUES 
('PB829LKS', 16, 3),
('PB829LKS', 19, 3),
('PB829LKS', 18, 3);

INSERT INTO prato_ingrediente_porcao (id_prato, id_ingrediente, id_porcao) VALUES 
('PD102938', 21, 2),
('PD102938', 22, 2),
('PD102938', 23, 2);

INSERT INTO prato_ingrediente_porcao (id_prato, id_ingrediente, id_porcao) VALUES 
('PG928374', 26, 1),
('PG928374', 25, 2),
('PG928374', 29, 3),
('PG928374', 27, 2); 

INSERT INTO prato_ingrediente_porcao (id_prato, id_ingrediente, id_porcao) VALUES 
('PH192837', 26, 2),
('PH192837', 29, 2),
('PH192837', 28, 2);

INSERT INTO prato_ingrediente_porcao (id_prato, id_ingrediente, id_porcao) VALUES 
('PK192837', 31, 1),
('PK192837', 1, 1),
('PK192837', 4, 1);

INSERT INTO prato_ingrediente_porcao (id_prato, id_ingrediente, id_porcao) VALUES 
('PL564738', 7, 2),
('PL564738', 21, 2),
('PL564738', 5, 2),
('PL564738', 6, 2);

INSERT INTO prato_ingrediente_porcao (id_prato, id_ingrediente, id_porcao) VALUES 
('PM918273', 32, 1),
('PM918273', 33, 3),
('PM918273', 34, 3),
('PM918273', 4, 2);

-- Define o formato de data para Ano-M�s-Dia apenas para esta execu��o
GO
SET DATEFORMAT ymd;

INSERT INTO pedido (valor, data, cpf_cliente, id_prato, id_porcao) VALUES
(30.00, '2023-11-19', '11122233344', 'P0001', 2), -- Jo�o, PF Carne M
(35.00, '2023-11-19', '22233344455', 'P0001', 3), -- Maria, PF Carne G
(28.00, '2023-11-19', '33344455566', 'P0003', 3), -- Carlos, X-Salada G
(23.00, '2023-11-19', '44455566677', 'P0003', 2), -- Ana, X-Salada M
(35.00, '2023-11-19', '55566677788', 'P0005', 2), -- Pedro, Espaguete M
(45.00, '2023-11-19', '66677788899', 'P0006', 3), -- Lucia, Lasanha G
(15.00, '2023-11-19', '77788899900', 'P0007', 1), -- Marcos, Salada P
(10.00, '2023-11-19', '88899900011', 'P0009', 1), -- Julia, Pudim P
(30.00, '2023-11-19', '99900011122', 'P0001', 2), -- Roberto, PF Carne M
(38.00, '2023-11-19', '00011122233', 'P0004', 3), -- Fernanda, X-Tudo G
(30.00, '2023-11-20', '11122233344', 'P0005', 2), -- Jo�o, Espaguete M
(12.00, '2023-11-20', '22233344455', 'P0010', 1), -- Maria, Mousse P
(25.00, '2023-11-20', '33344455566', 'P0001', 1), -- Carlos, PF Carne P
(32.00, '2023-11-20', '44455566677', 'P0008', 3), -- Ana, Salada Completa G
(28.00, '2023-11-20', '55566677788', 'P0002', 2); -- Pedro, PF Frango M

-- Pedidos de Mariana (11111111101)
INSERT INTO pedido (valor, data, cpf_cliente, id_prato, id_porcao) VALUES
(50.00, '2024-10-01', '11111111101', 'PA7X92B1', 2),
(25.00, '2024-10-05', '11111111101', 'PM918273', 1);

-- Pedidos de Bruno (22222222202)
INSERT INTO pedido (valor, data, cpf_cliente, id_prato, id_porcao) VALUES
(35.00, '2024-10-10', '22222222202', 'PB829LKS', 3),
(52.00, '2024-10-12', '22222222202', 'PG928374', 3);

-- Pedidos de Giovanna (33333333303)
INSERT INTO pedido (valor, data, cpf_cliente, id_prato, id_porcao) VALUES
(35.00, '2024-11-01', '33333333303', 'PD102938', 2),
(40.00, '2024-11-01', '33333333303', 'PK192837', 2);

-- Pedidos de Lázaro (44444444404)
INSERT INTO pedido (valor, data, cpf_cliente, id_prato, id_porcao) VALUES
(60.00, '2024-11-15', '44444444404', 'PH192837', 5)
(28.00, '2024-11-20', '44444444404', 'PI564738', 1);

-- Pedidos variados de Dezembro
INSERT INTO pedido (valor, data, cpf_cliente, id_prato, id_porcao) VALUES
(38.00, '2024-12-01', '55555555505', 'PJ918273', 1),
(31.00, '2024-12-02', '66666666606', 'PL564738', 2),
(25.00, '2024-12-03', '77777777707', 'PN192837', 3),
(13.00, '2024-12-04', '88888888808', 'PO564738', 1),
(45.00, '2024-12-05', '99999999909', 'PC92KD82', 3),
(22.00, '2024-12-06', '10101010110', 'PE918273', 1),
(25.00, '2024-12-07', '12121212112', 'PF564738', 2),
(50.00, '2024-12-08', '13131313113', 'PA7X92B1', 2),
(35.00, '2024-12-09', '14141414114', 'PB829LKS', 3),
(30.00, '2024-12-10', '15151515115', 'PD102938', 1),
(58.00, '2024-12-11', '16161616116', 'PJ918273', 3),
(35.00, '2024-12-12', '11111111101', 'PC92KD82', 1),
(45.00, '2024-12-13', '22222222202', 'PH192837', 2),
(33.00, '2024-12-14', '33333333303', 'PI564738', 2),
(21.00, '2024-12-15', '44444444404', 'PL564738', 3),
(45.00, '2024-12-25', '55555555505', 'PA7X92B1', 1);

INSERT INTO Pedido (id_prato, id_porcao, cpf_cliente, valor, data) VALUES
('P0010', 1, '11122233344', 24.0, GETDATE()),
('P0009', 3, '11122233344', 30.0, GETDATE())

GO
--Para os pratos em tela, uma UDF com cursores deve listar os pratos, cada qual com
--seus ingredientes.
CREATE FUNCTION fn_pratos_ingredientes()
RETURNS @tabela TABLE (
    id_prato            VARCHAR(10), -- Adicionado
    id_ingrediente      INT,         -- Adicionado
    id_porcao           INT,         -- Adicionado
    nome_prato          VARCHAR(50),
    nome_ingrediente    VARCHAR(50),
    nome_porcao         VARCHAR(50)
)
AS
BEGIN
    DECLARE @id_prato VARCHAR(10),
            @id_ingrediente INT,
            @id_porcao INT,
            @nome_prato VARCHAR(50),
            @nome_ingrediente VARCHAR(50),
            @nome_porcao VARCHAR(50)

    DECLARE c CURSOR FOR 
        SELECT 
            p.id, pip.id_ingrediente, pip.id_porcao, -- IDs
            p.nome, i.nome, po.tamanho               -- Nomes
        FROM Prato p 
        INNER JOIN Prato_Ingrediente_Porcao pip ON p.id = pip.id_prato
        INNER JOIN Ingrediente i ON pip.id_ingrediente = i.id
        INNER JOIN porcao po ON po.id = pip.id_porcao

    OPEN c
    FETCH NEXT FROM c INTO @id_prato, @id_ingrediente, @id_porcao, @nome_prato, @nome_ingrediente, @nome_porcao

    WHILE @@FETCH_STATUS = 0
    BEGIN
        INSERT INTO @tabela VALUES
        (@id_prato, @id_ingrediente, @id_porcao, @nome_prato, @nome_ingrediente, @nome_porcao)

        FETCH NEXT FROM c INTO @id_prato, @id_ingrediente, @id_porcao, @nome_prato, @nome_ingrediente, @nome_porcao
    END
    
    CLOSE c
    DEALLOCATE c

    RETURN
END

SELECT * FROM prato
--TESTE
SELECT nome_prato, nome_ingrediente, nome_porcao
FROM fn_pratos_ingredientes()
WHERE nome_prato = 'X-Salada'

--Cada prato tem um identificador �nico gerado aleatoriamente e iniciado pela letra P.

GO
CREATE PROCEDURE sp_gera_id_prato(@idPrato VARCHAR(10) OUTPUT)
AS
	DECLARE @i INT = 0

	WHILE @i = 0
	BEGIN
		SET @idPrato = 'P' + SUBSTRING(CONVERT(VARCHAR(MAX), NEWID()), 1, 8)

		IF (SELECT id FROM Prato WHERE id = @idPrato) IS NULL
		BEGIN
			SET @i = 1
		END
	END

--TESTE
DECLARE @teste VARCHAR(10)
EXEC sp_gera_id_prato @teste OUTPUT
PRINT(@teste)

--Deve-se poder gerar um relat�rio em PDF com os dados dos pratos, ingredientes,
--por��es e valores de um determinado tipo.
SELECT p.nome, p.valor, i.nome, i.formatoApresentacao, po.tamanho, po.valor, t.nome
FROM Tipo t INNER JOIN Prato p
ON t.id = p.id_tipo
INNER JOIN Prato_Ingrediente_Porcao pip
ON p.id = pip.id_prato
INNER JOIN Ingrediente i
ON i.id = pip.id_ingrediente
INNER JOIN Porcao po
ON po.id = pip.id_porcao
--WHERE t.nome = $P{tipo}--

--Deve-se poder gerar um relat�rio em PDF com os dados de um pedido (Com o valor
--total no cabe�alho), do pedido atual de um cliente.

SELECT pr.nome, po.tamanho, pe.valor, t.valor_total
FROM Prato pr INNER JOIN Pedido pe
ON pr.id = pe.id_prato
INNER JOIN Porcao po
ON po.id = pe.id_porcao
JOIN (SELECT id, SUM(valor) AS valor_total
      FROM Pedido
      GROUP BY id) t ON pe.id = t.id
	  -- WHERE pe.id = 1
      ORDER BY pe.id




--Deve-se poder gerar um relat�rio em PDF com os dados do pratos, ingredientes,
--por��o, valor e cliente de um determinado dia.

SELECT c.nome, pr.nome, i.nome, po.tamanho, pr.valor
FROM Prato pr INNER JOIN Prato_Ingrediente_Porcao pip
ON pr.id = pip.id_prato
INNER JOIN Pedido pe
ON pe.id_prato = pr.id
INNER JOIN Cliente c
ON c.cpf = pe.cpf_cliente
INNER JOIN Ingrediente i
ON i.id = pip.id_ingrediente
INNER JOIN Porcao po
ON po.id = pip.id_porcao
-- WHERE pe.data = '2023-11-19'