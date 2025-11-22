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

-- Garante que a tabela problemática seja recriada do zero pelo Hibernate com o tamanho certo
IF OBJECT_ID('dbo.prato_ingrediente_porcao', 'U') IS NOT NULL DROP TABLE dbo.prato_ingrediente_porcao;
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
('32132132132', 'Beatriz Nogueira', '11977776666', 'Rua Domingos de Morais', 45, '04110000', 'Shopping')
GO
INSERT INTO Tipo VALUES
('Prato Principal'),
('Lanche'),
('Sobremesa'),
('Bebida'),
('Entrada'),
('Salada')
GO
INSERT INTO Porcao VALUES
('Pequena', 0.0),
('Média', 1.5),
('Grande', 2.0)
GO
INSERT INTO Ingrediente VALUES
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
('Cebola', 'Caramelizada')
GO
INSERT INTO Prato VALUES
('P0001', 'Prato Feito (PF) Clássico', 24.0, 1),
('P0002', 'X-Salada', 15.0, 2),
('P0003', 'Macarronada à Bolonhesa', 22.0, 1),
('P0004', 'X-Bacon Especial', 20.0, 2),
('P0005', 'Salada Caesar', 18.0, 6),
('P0006', 'Bife a Cavalo', 36.0, 1),
('P0007', 'Frango Grelhado com Legumes', 28.0, 1),
('P0008', 'Misto Quente', 10.0, 2),
('P0009', 'Batata Frita Especial', 15.0, 5),
('P0010', 'Omelete Completo', 16.0, 1)

SELECT * FROM Prato
DELETE FROM Prato
GO
-- Define o formato de data para Ano-Mês-Dia apenas para esta execução
SET DATEFORMAT ymd;

INSERT INTO pedido (valor, data, cpf_cliente, id_prato, id_porcao) VALUES
(30.00, '2023-11-19', '11122233344', 'P0001', 2), -- João, PF Carne M
(35.00, '2023-11-19', '22233344455', 'P0001', 3), -- Maria, PF Carne G
(28.00, '2023-11-19', '33344455566', 'P0003', 3), -- Carlos, X-Salada G
(23.00, '2023-11-19', '44455566677', 'P0003', 2), -- Ana, X-Salada M
(35.00, '2023-11-19', '55566677788', 'P0005', 2), -- Pedro, Espaguete M
(45.00, '2023-11-19', '66677788899', 'P0006', 3), -- Lucia, Lasanha G
(15.00, '2023-11-19', '77788899900', 'P0007', 1), -- Marcos, Salada P
(10.00, '2023-11-19', '88899900011', 'P0009', 1), -- Julia, Pudim P
(30.00, '2023-11-19', '99900011122', 'P0001', 2), -- Roberto, PF Carne M
(38.00, '2023-11-19', '00011122233', 'P0004', 3), -- Fernanda, X-Tudo G
(30.00, '2023-11-20', '11122233344', 'P0005', 2), -- João, Espaguete M
(12.00, '2023-11-20', '22233344455', 'P0010', 1), -- Maria, Mousse P
(25.00, '2023-11-20', '33344455566', 'P0001', 1), -- Carlos, PF Carne P
(32.00, '2023-11-20', '44455566677', 'P0008', 3), -- Ana, Salada Completa G
(28.00, '2023-11-20', '55566677788', 'P0002', 2); -- Pedro, PF Frango M

GO
INSERT INTO Prato_Ingrediente_Porcao VALUES
('P0001', 1, 1),
('P0001', 2, 1),
('P0001', 3, 1),
('P0001', 4, 1)
GO
INSERT INTO Prato_Ingrediente_Porcao VALUES
('P0002', 8, 1),
('P0002', 9, 1),
('P0002', 7, 1),
('P0002', 5, 1),
('P0002', 6, 1)
GO
INSERT INTO Prato_Ingrediente_Porcao VALUES
('P0004', 8, 1),
('P0004', 9, 1),
('P0004', 7, 1),
('P0004', 13, 1)
GO
INSERT INTO Prato_Ingrediente_Porcao VALUES
('P0006', 3, 1),
('P0006', 14, 1),
('P0006', 1, 1)
GO
INSERT INTO Prato_Ingrediente_Porcao VALUES
('P0009', 4, 3),
('P0009', 13, 3),
('P0009', 7, 3)


--Para os pratos em tela, uma UDF com cursores deve listar os pratos, cada qual com
--seus ingredientes.

GO
CREATE FUNCTION fn_pratos_ingredientes()
RETURNS @tabela TABLE (
nome_prato			VARCHAR(50),
nome_ingrediente	VARCHAR(50)
)
AS
BEGIN
	DECLARE @nome_prato VARCHAR(50),
			@nome_ingrediente VARCHAR(50)

	DECLARE c CURSOR
		FOR SELECT p.nome, i.nome
			FROM Prato p INNER JOIN Prato_Ingrediente_Porcao pip
			ON p.id = pip.id_prato
			INNER JOIN Ingrediente i
			ON pip.id_ingrediente = i.id

	OPEN c
	FETCH NEXT FROM c
		INTO @nome_prato, @nome_ingrediente
	WHILE @@FETCH_STATUS = 0
	BEGIN
		INSERT INTO @tabela VALUES
		(@nome_prato, @nome_ingrediente)

		FETCH NEXT FROM c
		INTO @nome_prato, @nome_ingrediente
	END
	CLOSE c
	DEALLOCATE c

	RETURN
END

--TESTE
SELECT nome_prato, nome_ingrediente
FROM fn_pratos_ingredientes()
WHERE nome_prato = 'X-Salada'

--Cada prato tem um identificador único gerado aleatoriamente e iniciado pela letra P.

GO
CREATE PROCEDURE sp_gera_id_prato
AS
	DECLARE @idPrato VARCHAR(10),
			@i INT = 0

	WHILE @i = 0
	BEGIN
		SET @idPrato = 'P' + SUBSTRING(CONVERT(VARCHAR(MAX), NEWID()), 1, 8)

		IF (SELECT id FROM Prato WHERE id = @idPrato) IS NULL
		BEGIN
			SET @i = 1
		END
	END

--TESTE
EXEC sp_gera_id_prato

--Deve-se poder gerar um relatório em PDF com os dados dos pratos, ingredientes,
--porções e valores de um determinado tipo.

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

--Deve-se poder gerar um relatório em PDF com os dados de um pedido (Com o valor
--total no cabeçalho), do pedido atual de um cliente.

SELECT pr.nome, po.tamanho, pe.valor, t.valor_total
FROM Prato pr INNER JOIN Pedido pe
ON pr.id = pe.id_prato
INNER JOIN Porcao po
ON po.id = pe.id_porcao
JOIN (SELECT id, SUM(valor) AS valor_total
      FROM Pedido
      GROUP BY id) t ON pe.id = t.id
	  WHERE pe.id = 1
      ORDER BY pe.id

INSERT INTO Pedido (id_prato, id_porcao, cpf_cliente, valor, data) VALUES
('P0001', 1, '11122233344', 24.0, GETDATE()),
('P0009', 3, '11122233344', 30.0, GETDATE())

SELECT * FROM Pedido

--Deve-se poder gerar um relatório em PDF com os dados do pratos, ingredientes,
--porção, valor e cliente de um determinado dia.

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
WHERE pe.data = '2023-11-19'