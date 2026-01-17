# Sistema de Pedidos da Lanchonete | ![Java](https://img.shields.io/badge/Java-17+-red?logo=java)

O **Sistema de Pedidos da Lanchonete** é uma aplicação de console em Java que implementa um sistema simples para gerenciar um cardápio e pedidos de clientes. A aplicação permite cadastrar produtos (lanches, bebidas e sobremesas), criar pedidos para clientes, adicionar itens com quantidade, atualizar ou remover produtos do cardápio e finalizar o pedido exibindo o resumo e o total.

## Funcionalidades

- **Cadastro de produtos** com prevenção de duplicatas
- **Atributos de produto**:
  - Nome
  - Preço (BigDecimal)
  - Tipo (LANCHE / BEBIDA / SOBREMESA via classes concretas)
- **Listagem do cardápio** com formatação de moeda (pt-BR)
- **Fluxo de pedidos**:
  - Criar novo pedido para um cliente
  - Adicionar itens ao pedido (quantidade validada)
  - Finalizar pedido e exibir resumo com subtotais e total
- **Gerência do cardápio**:
  - Atualizar produto (nome, tipo, preço)
  - Remover produto do cardápio
- **Menu interativo** via console (`ConsoleMenu`)
- **Tratamento de erros** com exceções customizadas (produto duplicado, produto não encontrado, quantidade inválida, pedido não iniciado)

## Conceitos de Java utilizados

- Programação Orientada a Objetos (POO): classes de domínio bem definidas (`Product`, `Order`, `OrderItem`, `Customer`).
- Herança e polimorfismo: `Product` é uma superclasse abstrata; `Snack`, `Drink` e `Dessert` são subclasses.
- Interface: `PriceCalculable` usada para contrato de cálculo de preço.
- Coleções e mapas: `List`, `Map`, `ArrayList`, `HashMap`.
- Tratamento de erros com exceções customizadas.
- Manipulação de valores monetários com `BigDecimal`.
- Formatação de moeda com `NumberFormat` (Locale `pt-BR`).

## Estrutura de Arquivos

```
lanchonete-pedidos/
├── src/
│   └── br/
│       └── com/
│           └── snackshop/
│               ├── exception/
│               │   ├── DuplicateProductException.java
│               │   ├── InvalidQuantityException.java
│               │   ├── OrderNotStartedException.java
│               │   └── ProductNotFoundException.java
│               ├── model/
│               │   ├── Category.java
│               │   ├── Customer.java
│               │   ├── Dessert.java
│               │   ├── Drink.java
│               │   ├── Order.java
│               │   ├── OrderItem.java
│               │   ├── Product.java
│               │   └── Snack.java
│               └── presentation/
│                   └── ConsoleMenu.java
│               └── service/
│                   ├── MenuService.java
│                   ├── OrderService.java
│                   └── PriceCalculable.java
└── README.md
```

## Operações (Mapeadas para CRUD do cardápio/pedidos)

### Create (Criar)
- **Cadastrar produto**: opção do menu para adicionar um novo `Product` (`Snack`, `Drink`, `Dessert`). O `MenuService` garante que não haja duplicatas com base no nome.

### Read (Ler)
- **Listar cardápio**: exibe todos os produtos cadastrados com seus preços.

### Update (Atualizar)
- **Atualizar produto**: permite alterar nome, tipo e preço de um produto existente. Regras:
  - Se o produto antigo não existir, `ProductNotFoundException` é lançada.
  - Se o novo nome conflitar com outro produto já existente, `DuplicateProductException` é lançada.
  - A operação preserva validações do construtor de `Product` (nome não vazio, preço não negativo).

### Delete (Deletar)
- **Remover produto**: remove um produto do cardápio por nome. Se não existir, `ProductNotFoundException` é lançada.

## Como executar

**Pré-requisito**: JDK 17 ou superior instalado.

1. Abra o PowerShell na pasta raiz do projeto (onde está a pasta `src`).
2. Compile o projeto criando um diretório `out`:

```powershell
mkdir out; javac -d out (Get-ChildItem -Path .\src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

3. Execute a aplicação:

```powershell
java -cp out br.com.snackshop.presentation.ConsoleMenu
```

### Alternativa usando IDE

1. Abra o projeto em uma IDE como IntelliJ IDEA ou Eclipse.
2. Execute a classe `br.com.snackshop.presentation.ConsoleMenu` diretamente pela IDE.

