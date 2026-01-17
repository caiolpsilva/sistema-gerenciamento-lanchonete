package br.com.snackshop.service;
import br.com.snackshop.exception.DuplicateProductException;
import br.com.snackshop.exception.ProductNotFoundException;
import br.com.snackshop.model.Product;
import java.util.*;

public class MenuService {
    private final Map<String, Product> cardapio = new HashMap<>();

    public void cadastrar(Product produto) {
        String chave = produto.getNome().toLowerCase().trim();
        if (cardapio.containsKey(chave)) {
            throw new DuplicateProductException("Produto '" + produto.getNome() + "' já existe no cardápio.");
        }
        cardapio.put(chave, produto);
    }

    public Product buscarPorNome(String nome) {
        String chave = nome.toLowerCase().trim();
        Product p = cardapio.get(chave);
        if (p == null) {
            throw new ProductNotFoundException("Produto '" + nome + "' não encontrado no cardápio.");
        }
        return p;
    }
    public void remover(String nome) {
        String chave = nome.toLowerCase().trim();
        Product removed = cardapio.remove(chave);
        if (removed == null) {
            throw new ProductNotFoundException("Produto '" + nome + "' não encontrado no cardápio.");
        }
    }

    public void atualizar(String nomeExistente, Product produtoAtualizado) {
        String chaveAntiga = nomeExistente.toLowerCase().trim();
        if (!cardapio.containsKey(chaveAntiga)) {
            throw new ProductNotFoundException("Produto '" + nomeExistente + "' não encontrado no cardápio.");
        }
        String chaveNova = produtoAtualizado.getNome().toLowerCase().trim();
        if (!chaveNova.equals(chaveAntiga) && cardapio.containsKey(chaveNova)) {
            throw new DuplicateProductException("Produto '" + produtoAtualizado.getNome() + "' já existe no cardápio.");
        }
        cardapio.remove(chaveAntiga);
        cardapio.put(chaveNova, produtoAtualizado);
    }


    public List<Product> listarTodos() {
        return new ArrayList<>(cardapio.values());
    }
}
