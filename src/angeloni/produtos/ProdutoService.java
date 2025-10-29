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

    public void editarProduto(int id, double novoPreco) {
        Produto produto = consultarProduto(id);
        if (produto != null) {
            produto.setPreco(novoPreco);
            System.out.println("Produto atualizado: " + produto);
        } else {
            System.out.println("Não foi possível editar o produto.");
        }
    }

    public void entradaEstoque(int id, int quantidade) {
        Produto produto = consultarProduto(id);
        if (produto != null) {
            if (quantidade > 0) {
                int novoEstoque = produto.getQtdEstoque() + quantidade;
                produto.setQtdEstoque(novoEstoque);
                System.out.println("Entrada registrada. Novo estoque de " + produto.getNome() + ": " + novoEstoque);
            } else {
                System.out.println("Quantidade inválida. Deve ser maior que zero.");
            }
        } else {
            System.out.println("Não foi possível registrar a entrada.");
        }
    }

    public boolean saidaEstoque(int id, int quantidade) {
        Produto produto = consultarProduto(id);
        if (produto != null) {
            if (quantidade > 0) {
                if (produto.getQtdEstoque() >= quantidade) {
                    int novoEstoque = produto.getQtdEstoque() - quantidade;
                    produto.setQtdEstoque(novoEstoque);
                    System.out.println("Saída registrada. Novo estoque de " + produto.getNome() + ": " + novoEstoque);
                    return true;
                } else {
                    System.out.println("Estoque insuficiente. Disponível: " + produto.getQtdEstoque());
                    return false;
                }
            } else {
                System.out.println("Quantidade inválida. Deve ser maior que zero.");
                return false;
            }
        } else {
            System.out.println("Não foi possível registrar a saída.");
            return false;
        }
    }

    public void baixaEstoque(int id, int quantidade, String motivo) {
        Produto produto = consultarProduto(id);
        if (produto != null) {
            if (quantidade > 0) {
                if (produto.getQtdEstoque() >= quantidade) {
                    int novoEstoque = produto.getQtdEstoque() - quantidade;
                    produto.setQtdEstoque(novoEstoque);
                    System.out.println("Baixa registrada. Motivo: " + motivo);
                    System.out.println("Novo estoque de " + produto.getNome() + ": " + novoEstoque);
                } else {
                    System.out.println("Quantidade de baixa maior que estoque disponível.");
                }
            } else {
                System.out.println("Quantidade inválida. Deve ser maior que zero.");
            }
        } else {
            System.out.println("Não foi possível registrar a baixa.");
        }
    }

}
