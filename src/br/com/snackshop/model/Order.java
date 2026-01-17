package br.com.snackshop.model;

import br.com.snackshop.service.PriceCalculable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order implements PriceCalculable {

    private final Customer cliente;
    private final List<OrderItem> itens = new ArrayList<>();

    public Order(Customer cliente) {
        this.cliente = Objects.requireNonNull(cliente, "Cliente não pode ser nulo");
    }

    public void adicionarItem(OrderItem item) {
        itens.add(Objects.requireNonNull(item, "Item não pode ser nulo"));
    }

    public Customer getCliente() {
        return cliente;
    }

    public List<OrderItem> getItens() {
        return Collections.unmodifiableList(itens);
    }

    @Override
    public BigDecimal calcularTotal() {
        return itens.stream()
                .map(OrderItem::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
