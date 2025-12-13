# Avaliação 3 de Laboratório de Banco de Dados (Sistema de Delivery)

## Enunciado
- Considerando o domínio a seguir, montar a modelagem (Diagramas de Classe e ER) para o ambiente ser desenvolvido em Java Web (Obrigatoriamente com Spring Boot, Spring Web e Spring Data JPA).
  - Uma empresa de delivery vende pratos de comidas diversos. Cada prato tem um identificador único gerado aleatoriamente e iniciado pela letra P, além de um nome. Os pratos são cadastrados por tipo e são vendidos em porções, que definem seu valor (pequeno, médio e grande). Todos os pratos são compostos por diversos ingredientes, que tem um identificador único, um nome e um formato de apresentação. Um ingrediente pode aparecer em diversos pratos, mas em porções diferentes. Um cliente pode selecionar vários pratos, de várias porções para compra, mas para finalizar a compra, deve estar cadastrado no sistema por seu CPF, nome, telefone, endereço composto de logradouro, número e CEP, bem como um ponto de referência para entrega. No pedido, além dos pratos que o cliente selecionou, deve-se saber a data de realização. Ao finalizar o pedido, o cliente deve verificar o que foi comprado e o valor total em tela.
- A aplicação deve ter um CRUD (view, model, controller e repository) para cada entidade/objeto definido com suas regras estabelecidas.
- Para os pratos em tela, uma UDF com cursores deve listar os pratos, cada qual com seus ingredientes.
- Deve-se poder gerar um relatório em PDF com os dados dos pratos, ingredientes, porções e valores de um determinado tipo.
- Deve-se poder gerar um relatório em PDF com os dados de um pedido (Com o valor total no cabeçalho), do pedido atual de um cliente.
- Deve-se poder gerar um relatório em PDF com os dados do pratos, ingredientes, porção, valor e cliente de um determinado dia.
- Todas as consultas citadas acima devem estar disponíveis na camada view e tratadas pela camada controller.

## Princípios SOLID do Projeto

A arquitetura deste projeto foi desenhada seguindo os princípios do SOLID para garantir manutenibilidade, escalabilidade e desacoplamento entre as camadas do Spring Boot. Abaixo, detalhamos como cada princípio foi aplicado na prática, considerando as entidades e as funcionalidades implementadas.

### S - Single Responsibility Principle (Princípio da Responsabilidade Única)

"Uma classe deve ter apenas um motivo para mudar."

- No projeto, cada classe possui uma responsabilidade única e bem definida, respeitando a arquitetura em camadas (MVC):

  - Controllers (PedidoController, PratoController): Responsáveis exclusivamente por gerenciar as requisições HTTP/Web, interagir com a View (Thymeleaf) e delegar a lógica de negócios. Eles não contêm regras de validação complexas, não acessam o banco de dados diretamente e não geram arquivos PDF (apenas chamam quem gera).

  - Services (PratoService, PedidoService): Concentram toda a regra de negócio. É aqui que ocorrem as validações, as chamadas de Stored Procedures (como a sp_gera_id_prato) e a orquestração do salvamento de dados.

  - Repositories (PedidoRepository, PratoRepository): Responsáveis apenas pela comunicação com o banco de dados SQL Server, abstraindo a complexidade das queries SQL e chamadas de procedures.

  - DTOs (AtualizacaoPrato, AtualizacaoPedido): Responsáveis apenas por transportar dados entre o formulário (Front-end) e o Backend, isolando a estrutura interna da Entidade JPA da camada de apresentação.

  - Fronteiras: Existe uma subdivisão interna entre algumas Entidades, em que cada html tem uma ação específica dentro do CRUD: O listagem (Read/Delete/Relatório em alguns casos) e o formulário (Create/Update)

### O - Open/Closed Principle (Princípio Aberto/Fechado)

"Entidades de software devem estar abertas para extensão, mas fechadas para modificação."

- O sistema permite adicionar novos comportamentos sem alterar o código fonte dos componentes principais:
  - Extensão de Repositories: Utilizamos interfaces (como Repositorys) que estendem JpaRepository. Adicionamos novas funcionalidades — como a chamada da function nativa findAllViaFunction ou Procedures com @Procedure — criando novos métodos na interface, sem precisar alterar a implementação interna do Spring Data JPA.
  - Relatórios Jasper: A arquitetura de geração de relatórios foi feita de forma modular. Para adicionar um novo relatório (ex: "Vendas por Mês"), basta criar um novo arquivo .jrxml e um novo endpoint no Controller, sem a necessidade de modificar a lógica de conexão com o DataSource ou a estrutura das entidades existentes.

### L - Liskov Substitution Principle (Princípio da Substituição de Liskov)

"Objetos em um programa devem ser substituíveis por instâncias de seus subtipos sem alterar a integridade do programa."

- Este princípio é aplicado através do uso de Contratos (Interfaces) e Polimorfismo:

  - Interface Genérica IService: Definimos a interface IService<T, DTO, ID>, que estabelece o contrato para as operações CRUD (Salvar, Listar, Excluir, Buscar).
  - Implementação: As classes PratoService, PedidoService e PorcaoService implementam esse contrato. Isso garante que, em qualquer lugar do sistema que espere um serviço CRUD padrão, qualquer uma dessas implementações possa ser utilizada, mantendo a consistência comportamental da aplicação e garantindo que métodos como salvarOuAtualizar se comportem de maneira previsível.

### I - Interface Segregation Principle (Princípio da Segregação de Interface)

"Muitas interfaces específicas são melhores do que uma interface única e geral."

- O projeto evita forçar as classes (ou o Front-end) a dependerem de dados ou métodos que não utilizam, criando interfaces específicas para visualizações específicas (Projeções):

  - Projeções JPA (PratoIngredienteView): Para a listagem da "Ficha Técnica", não obrigamos o sistema a carregar a entidade completa PratoIngrediente (que possui relacionamentos pesados e complexos com Prato, Ingrediente e Porção).
  - Aplicação: Criamos uma interface leve (PratoIngredienteView) que expõe apenas os dados necessários para aquela tela (getNomePrato, getIdPrato, etc.). Isso separa a interface de Leitura (View) da interface de Escrita (Entidade/DTO), otimizando a performance e simplificando o uso dos dados no Thymeleaf. O cliente (a View HTML) não precisa conhecer a complexidade da entidade de domínio.

### D - Dependency Inversion Principle (Princípio da Inversão de Dependência)

"Módulos de alto nível não devem depender de módulos de baixo nível. Ambos devem depender de abstrações"

- O desacoplamento entre as camadas é garantido pela Injeção de Dependência do Spring (IoC):
  - Uso de @Autowired: As classes de alto nível (como PedidoController) não instanciam manualmente suas dependências de baixo nível (new PedidoService()). Em vez disso, elas declaram uma dependência que é injetada automaticamente pelo container do Spring. Isso facilita a troca de implementações e a criação de testes unitários (Mocks).
  - Abstração DataSource: Para a geração de relatórios Jasper, injetamos a interface javax.sql.DataSource (uma abstração do JDBC), sem que o controlador precise saber os detalhes concretos da conexão (driver, url, senha) ou se o banco é SQL Server, Oracle ou MySQL. O código depende da abstração de conexão, não da implementação específica do driver.

  - Abstração de Repositories: Os Services dependem das interfaces dos Repositories, e não de uma classe concreta de acesso a dados. O Spring Data cria a implementação concreta (SimpleJpaRepository ou um Proxy) em tempo de execução. Isso significa que a regra de negócio depende de um contrato abstrato de persistência (Como "eu preciso de algo que salve Pratos", por exemplo), e não de como o Hibernate ou o SQL Server realizam essa tarefa fisicamente.
 
## Diagramas

### DIagrama Entidade Relacionamento (DER)
![DIagrama Entidade Relacionamento (DER)](./doc/DER.jpg)

### Diagrama de Classe de Projeto (VCP - Visão de Classes Participantes)
![Diagrama de Classes](./doc/VCP.jpg)

## Telas do Projeto

### Dashboard
![Diagrama de Classes](https://github.com/user-attachments/assets/d8a42314-ea14-4b0d-8ed3-8b6e3847a5c5)

### Porções
<img width="669" height="333" alt="image" src="https://github.com/user-attachments/assets/ce46b784-1bbd-49d8-8502-5e7e57e30d2b" />

<img width="607" height="226" alt="image" src="https://github.com/user-attachments/assets/6f8d8b35-1b36-43bb-a030-df9511a61cfb" />

### Tipos de Pratos
<img width="729" height="505" alt="image" src="https://github.com/user-attachments/assets/fabde735-900a-46aa-96e7-394cf9d62a4f" />
<img width="781" height="177" alt="image" src="https://github.com/user-attachments/assets/8840a6f1-9f93-4278-923e-222e068ef990" />

### Ingredientes
<img width="752" height="672" alt="image" src="https://github.com/user-attachments/assets/4f3f6635-3d57-4794-a043-e44e376ad043" />
<img width="755" height="249" alt="image" src="https://github.com/user-attachments/assets/070e26e3-f463-4efc-a071-bedd915ecc4c" />

### Clientes
<img width="784" height="548" alt="image" src="https://github.com/user-attachments/assets/fa4998e9-b339-49fe-b49b-a276695d9833" />
<img width="731" height="437" alt="image" src="https://github.com/user-attachments/assets/abc46129-742e-4ac2-a468-0e3296e3937b" />

### Pratos
<img width="721" height="508" alt="image" src="https://github.com/user-attachments/assets/db7ef654-40a6-4d4b-85a2-c35dcc4aa9ca" />
<img width="782" height="302" alt="image" src="https://github.com/user-attachments/assets/77017c2c-958f-4de7-ad2a-122056b71a03" />

### Pedidos
<img width="727" height="204" alt="image" src="https://github.com/user-attachments/assets/95645eb5-5a02-4646-9ef1-f31e1283338e" />
<img width="785" height="316" alt="image" src="https://github.com/user-attachments/assets/57214a31-d0b5-4ebd-a73d-e30c42b7ee7f" />

<img width="778" height="254" alt="image" src="https://github.com/user-attachments/assets/0db59a27-6652-43b4-81b1-2a156b7efa86" />

### Fichas Técnicas
<img width="1439" height="700" alt="image" src="https://github.com/user-attachments/assets/604de2ce-49b6-4968-867e-70d6445b8fb6" />
<img width="389" height="309" alt="image" src="https://github.com/user-attachments/assets/75f844d8-2b02-4e0c-82b9-864d53d93471" />

## Relatórios

### Tipo de Prato

<img width="836" height="219" alt="image" src="https://github.com/user-attachments/assets/5fe6bc7c-7b60-4133-b793-637197ddd104" />

### Pedido
<img width="586" height="239" alt="image" src="https://github.com/user-attachments/assets/597e2531-9030-4c4f-b6b9-ce4383de739d" />

### Cliente Data
<img width="829" height="203" alt="image" src="https://github.com/user-attachments/assets/fac8efde-6f75-4a6e-b492-123527a3f4c2" />


<b> Créditos: [@GustavoPereira-DeV](https://github.com/GustavoPereira-Dev) (Projeto Java) e [@Joaoftito](https://github.com/Joaoftito) (Diagramação/Modelagem, SQL e Relatórios)
