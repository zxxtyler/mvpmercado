package angeloni.produtos;

import java.util.ArrayList;
import java.util.List;

public class ProdutoService {
    private List<Produto> produtos = new ArrayList<>();

    public void cadastrarProduto(Produto produto) {
        produtos.add(produto);
    }

    public Produto consultarProduto(int id) {
        for (Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        System.out.println("Produto não encontrado.");
        return null;
    }

    public void excluirProduto(int id) {
        Produto produto = consultarProduto(id);
        if (produto != null) {
            produtos.remove(produto);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    public void listarProdutos() {
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

}
