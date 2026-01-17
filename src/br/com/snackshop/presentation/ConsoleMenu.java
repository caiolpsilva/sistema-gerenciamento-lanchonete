package br.com.snackshop.presentation;

import br.com.snackshop.exception.*;
import br.com.snackshop.model.*;
import br.com.snackshop.service.MenuService;
import br.com.snackshop.service.OrderService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ConsoleMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MenuService cardapioService = new MenuService();
    private static final OrderService pedidoService = new OrderService(cardapioService);
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static void main(String[] args) {
        while (true) {
            exibirMenu();
            int opcao = lerInteiro();
            try {
                switch (opcao) {
                    case 1 -> cadastrarProduto();
                    case 2 -> listarCardapio();
                    case 3 -> criarPedido();
                    case 4 -> adicionarItem();
                    case 5 -> finalizarPedido();
                    case 7 -> atualizarProduto();
                    case 8 -> removerProduto();
                    case 6 -> {
                        System.out.println("\nObrigado por usar a Lanchonete! Até logo!");
                        return;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("\nErro: " + e.getMessage());
            }
            System.out.println();
        }
    }

    private static void exibirMenu() {
        System.out.println("=== Lanchonete===");
        System.out.println("1. Cadastrar produto");
        System.out.println("2. Listar cardápio");
        System.out.println("3. Criar novo pedido");
        System.out.println("4. Adicionar item ao pedido");
        System.out.println("5. Finalizar pedido");
        System.out.println("7. Atualizar produto");
        System.out.println("8. Remover produto");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarProduto() {
        System.out.print("Digite o tipo de produto (LANCHE / BEBIDA / SOBREMESA): ");
        String tipo = scanner.nextLine().trim().toUpperCase();
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Preço (ex: 12.90): ");
        BigDecimal preco = new BigDecimal(scanner.nextLine().replace(',', '.'));

        Product produto;
        switch (tipo) {
            case "LANCHE" -> produto = new Snack(nome, preco);
            case "BEBIDA" -> produto = new Drink(nome, preco);
            case "SOBREMESA" -> produto = new Dessert(nome, preco);
            default -> {
                System.out.println("Tipo inválido.");
                return;
            }
        }
        cardapioService.cadastrar(produto);
        System.out.println("Produto '" + nome + "' cadastrado com sucesso!");
    }

    private static void listarCardapio() {
        List<Product> produtos = cardapioService.listarTodos();
        if (produtos.isEmpty()) {
            System.out.println("Cardápio vazio.");
            return;
        }
        System.out.println("CARDÁPIO:");
        for (Product p : produtos) {
            System.out.println("• " + p.getNome() + " (" + p.getClass().getSimpleName().toUpperCase() + ") – " + currencyFormat.format(p.getPreco()));
        }
    }

    private static void criarPedido() {
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();
        Customer cliente = new Customer(nome);
        pedidoService.iniciarNovoPedido(cliente);
        System.out.println("Pedido criado para: " + cliente.getNome());
    }

    private static void adicionarItem() {
        listarCardapio();
        System.out.print("\nDigite o nome exato do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Quantidade: ");
        int qtd = lerInteiro();
        pedidoService.adicionarItemAoPedido(nome, qtd);
        System.out.println("Item adicionado: " + qtd + "x " + nome);
    }

    private static void finalizarPedido() {
        Order pedido = pedidoService.finalizarPedido();
        System.out.println("\n--- RESUMO DO PEDIDO ---");
        System.out.println("Cliente: " + pedido.getCliente().getNome());
        System.out.println("Itens:");
        for (OrderItem item : pedido.getItens()) {
            System.out.println("  • " + item.getQuantidade() + "x " +
                    item.getProduto().getNome() +
                    " (" + currencyFormat.format(item.getProduto().getPreco()) + ") → " +
                    currencyFormat.format(item.calcularSubtotal()));
        }
        System.out.println("Total: " + currencyFormat.format(pedido.calcularTotal()));
        System.out.println("Pedido finalizado com sucesso!");
    }

    private static void atualizarProduto() {
        System.out.print("Digite o nome exato do produto a ser atualizado: ");
        String nomeAntigo = scanner.nextLine();
        Product existente = cardapioService.buscarPorNome(nomeAntigo);

        String tipoAtual;
        if (existente instanceof Snack) tipoAtual = "LANCHE";
        else if (existente instanceof Drink) tipoAtual = "BEBIDA";
        else tipoAtual = "SOBREMESA";

        System.out.print("Novo tipo (LANCHE / BEBIDA / SOBREMESA) [enter = manter " + tipoAtual + "]: ");
        String tipoInput = scanner.nextLine().trim().toUpperCase();
        String tipo = tipoInput.isEmpty() ? tipoAtual : tipoInput;

        System.out.print("Novo nome [enter = manter '" + existente.getNome() + "']: ");
        String novoNomeInput = scanner.nextLine().trim();
        String novoNome = novoNomeInput.isEmpty() ? existente.getNome() : novoNomeInput;

        System.out.print("Novo preço (ex: 12.90) [enter = manter " + existente.getPreco() + "]: ");
        String precoInput = scanner.nextLine().trim();
        BigDecimal novoPreco;
        if (precoInput.isEmpty()) {
            novoPreco = existente.getPreco();
        } else {
            try {
                novoPreco = new BigDecimal(precoInput.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido. Atualização cancelada.");
                return;
            }
        }

        Product novoProduto;
        switch (tipo) {
            case "LANCHE" -> novoProduto = new Snack(novoNome, novoPreco);
            case "BEBIDA" -> novoProduto = new Drink(novoNome, novoPreco);
            case "SOBREMESA" -> novoProduto = new Dessert(novoNome, novoPreco);
            default -> {
                System.out.println("Tipo inválido. Atualização cancelada.");
                return;
            }
        }

        cardapioService.atualizar(nomeAntigo, novoProduto);
        System.out.println("Produto atualizado com sucesso!");
    }

    private static void removerProduto() {
        System.out.print("Digite o nome exato do produto a remover: ");
        String nome = scanner.nextLine();
        cardapioService.remover(nome);
        System.out.println("Produto '" + nome + "' removido com sucesso!");
    }


    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
