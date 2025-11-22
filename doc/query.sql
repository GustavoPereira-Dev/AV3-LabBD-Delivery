CREATE DATABASE DeliveryAV3
GO
USE DeliveryAV3

GO
CREATE TABLE Tipo (
id			INT				NOT NULL,
nome		VARCHAR(50)		NOT NULL
PRIMARY KEY(id)
)

GO
CREATE TABLE Prato (
id			VARCHAR(10)		NOT NULL,
nome		VARCHAR(50)		NOT NULL,
valor		DECIMAL(7,2)	NOT NULL,
id_tipo		INT				NOT NULL
PRIMARY KEY(id)
FOREIGN KEY(id_tipo) REFERENCES Tipo(id)
)

GO
CREATE TABLE Cliente (
cpf					VARCHAR(11)		NOT NULL,
nome				VARCHAR(100)	NOT NULL,
telefone			VARCHAR(11)		NOT NULL,
endereco			VARCHAR(100)	NOT NULL,
numero				INT				NOT NULL,
cep					VARCHAR(8)		NOT NULL,
pontoReferencia		VARCHAR(100)
PRIMARY KEY(cpf)
)

GO
CREATE TABLE Porcao (
id			INT				NOT NULL,
tamanho		VARCHAR(10)		NOT NULL,
valor		DECIMAL(7,2)	NOT NULL
PRIMARY KEY(id)
)

GO
CREATE TABLE Pedido (
id_pedido		INT				NOT NULL,
id_prato		VARCHAR(10)		NOT NULL,
id_porcao		INT				NOT NULL,
cpf_cliente		VARCHAR(11)		NOT NULL,
valor			DECIMAL(7,2)	NOT NULL,
data_     		DATE		    NOT NULL
PRIMARY KEY(id_pedido, id_prato, id_porcao, cpf_cliente)
FOREIGN KEY(id_prato) REFERENCES Prato(id),
FOREIGN KEY(id_porcao) REFERENCES Porcao(id),
FOREIGN KEY(cpf_cliente) REFERENCES Cliente(cpf)
)

GO
CREATE TABLE Ingrediente (
id						INT				NOT NULL,
nome					VARCHAR(50)		NOT NULL,
formatoApresentacao		VARCHAR(50)		NOT NULL
PRIMARY KEY(id)
)

GO
CREATE TABLE Prato_Ingrediente_Porcao (
id_prato			VARCHAR(10)		NOT NULL,
id_ingrediente		INT				NOT NULL,
id_porcao			INT				NOT NULL
PRIMARY KEY(id_prato, id_ingrediente, id_porcao)
FOREIGN KEY(id_prato) REFERENCES Prato(id),
FOREIGN KEY(id_ingrediente) REFERENCES Ingrediente(id),
FOREIGN KEY(id_porcao) REFERENCES Porcao(id)
)

--DADOS
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

INSERT INTO Tipo VALUES
(1, 'Prato Principal'),
(2, 'Lanche'),
(3, 'Sobremesa'),
(4, 'Bebida'),
(5, 'Entrada'),
(6, 'Salada')

INSERT INTO Porcao VALUES
(1, 'Pequena', 0.0),
(2, 'Média', 1.5),
(3, 'Grande', 2.0)

INSERT INTO Ingrediente VALUES
(1, 'Arroz Branco', 'Cozido'),
(2, 'Feijão Carioca', 'Cozido'),
(3, 'Bife de Alcatra', 'Grelhado'),
(4, 'Batata Frita', 'Palito'),
(5, 'Alface Americana', 'Lavada/Folha'),
(6, 'Tomate', 'Rodelas'),
(7, 'Queijo Mussarela', 'Fatiado'),
(8, 'Pão de Hambúrguer', 'Cortado'),
(9, 'Hambúrguer Bovino', 'Grelhado 150g'),
(10, 'Macarrão Espaguete', 'Cozido'),
(11, 'Molho de Tomate', 'Líquido'),
(12, 'Frango Grelhado', 'Filé'),
(13, 'Bacon', 'Cubos Fritos'),
(14, 'Ovo', 'Frito'),
(15, 'Cebola', 'Caramelizada')

INSERT INTO Prato VALUES
(1, 'Prato Feito (PF) Clássico', 24.0, 1),
(2, 'X-Salada', 15.0, 2),
(3, 'Macarronada à Bolonhesa', 22.0, 1),
(4, 'X-Bacon Especial', 20.0, 2),
(5, 'Salada Caesar', 18.0, 6),
(6, 'Bife a Cavalo', 36.0, 1),
(7, 'Frango Grelhado com Legumes', 28.0, 1),
(8, 'Misto Quente', 10.0, 2),
(9, 'Batata Frita Especial', 15.0, 5),
(10, 'Omelete Completo', 16.0, 1)

INSERT INTO Prato_Ingrediente_Porcao VALUES
(1, 1, 1),
(1, 2, 1),
(1, 3, 1),
(1, 4, 1)

INSERT INTO Prato_Ingrediente_Porcao VALUES
(2, 8, 1),
(2, 9, 1),
(2, 7, 1),
(2, 5, 1),
(2, 6, 1)

INSERT INTO Prato_Ingrediente_Porcao VALUES
(4, 8, 1),
(4, 9, 1),
(4, 7, 1),
(4, 13, 1)

INSERT INTO Prato_Ingrediente_Porcao VALUES
(6, 3, 1),
(6, 14, 1),
(6, 1, 1)

INSERT INTO Prato_Ingrediente_Porcao VALUES
(9, 4, 3),
(9, 13, 3),
(9, 7, 3)

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

SELECT p.nome, p.valor, i.nome, i.formatoApresentacao, po.tamanho, po.valor
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
JOIN (SELECT id_pedido, SUM(valor) AS valor_total
      FROM Pedido
      GROUP BY id_pedido) t ON pe.id_pedido = t.id_pedido
	  --WHERE pe.id_pedido = $P{id_pedido}--
      ORDER BY pe.id_pedido

INSERT INTO Pedido VALUES
(1000, 1, 1, '11122233344', 24.0, GETDATE()),
(1000, 9, 3, '11122233344', 30.0, GETDATE())

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
--WHERE pe.data_ = $P{data}--