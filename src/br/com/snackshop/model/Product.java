package br.com.snackshop.model;
import java.math.BigDecimal;
import java.util.Objects;

public abstract class Product {
    private final String nome;
    private final BigDecimal preco;

    public Product(String nome, BigDecimal preco) {
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo").trim();
        if (this.nome.isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio");
        this.preco = Objects.requireNonNull(preco, "Preço não pode ser nulo");
        if (preco.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Preço não pode ser negativo");
    }

    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product p)) return false;
        return nome.equalsIgnoreCase(p.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return nome + " – R$" + preco;
    }
}

