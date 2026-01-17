package br.com.snackshop.model;
import java.util.Objects;

public class Customer {

    private final String nome;
    public Customer(String nome) {
        this.nome = Objects.requireNonNull(nome, "Nome do cliente não pode ser nulo").trim();
        if (this.nome.isEmpty()) throw new IllegalArgumentException("Nome do cliente não pode ser vazio");
    }

    public String getNome() { return nome; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer c)) return false;
        return nome.equalsIgnoreCase(c.nome);
    }

    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return nome;
    }
}
