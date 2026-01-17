package br.com.snackshop.model;
import java.math.BigDecimal;

public class OrderItem {
    private final Product product;
    private final int quantidade;

    public OrderItem(Product product, int quantidade) {
        this.product = java.util.Objects.requireNonNull(product, "Produto não pode ser nulo");
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        this.quantidade = quantidade;
    }

    public Product getProduto() { return product; }
    public int getQuantidade() { return quantidade; }

    public BigDecimal calcularSubtotal() {
        return product.getPreco().multiply(BigDecimal.valueOf(quantidade));
    }
}
