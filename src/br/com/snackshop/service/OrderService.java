package br.com.snackshop.service;
import br.com.snackshop.exception.InvalidQuantityException;
import br.com.snackshop.exception.OrderNotStartedException;
import br.com.snackshop.model.Customer;
import br.com.snackshop.model.OrderItem;
import br.com.snackshop.model.Order;
import br.com.snackshop.model.Product;

public class OrderService {
    private MenuService cardapioService;
    private Order pedidoAtivo;

    public OrderService(MenuService cardapioService) {
        this.cardapioService = cardapioService;
    }

    public void iniciarNovoPedido(Customer cliente) {
        this.pedidoAtivo = new Order(cliente);
    }

    public void adicionarItemAoPedido(String nomeProduto, int quantidade) {
        if (pedidoAtivo == null) {
            throw new OrderNotStartedException("Nenhum pedido ativo. Crie um novo pedido primeiro.");
        }
        if (quantidade <= 0) {
            throw new InvalidQuantityException("Quantidade deve ser maior que zero.");
        }
        Product produto = cardapioService.buscarPorNome(nomeProduto);
        OrderItem item = new OrderItem(produto, quantidade);
        pedidoAtivo.adicionarItem(item);
    }

    public Order finalizarPedido() {
        if (pedidoAtivo == null) {
            throw new OrderNotStartedException("Nenhum pedido ativo para finalizar.");
        }
        Order finalizado = pedidoAtivo;
        pedidoAtivo = null;
        return finalizado;
    }

}
