package angeloni.produtos;

import angeloni.database.ProdutoData;
import java.util.List;

public class ProdutoService {
    private ProdutoData produtoData = new ProdutoData();

    public void cadastrarProduto(Produto produto) {
        if (produto == null) {
            System.out.println("Erro: Produto não pode ser nulo.");
            return;
        }
        if (produto.getId() <= 0) {
            System.out.println("Erro: ID do produto deve ser maior que zero.");
            return;
        }
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            System.out.println("Erro: Nome do produto não pode ser vazio.");
            return;
        }
        if (produto.getPreco() < 0) {
            System.out.println("Erro: Preço não pode ser negativo.");
            return;
        }


        if (consultarProduto(produto.getId()) != null) {
            System.out.println("Erro: Já existe um produto com esse ID.");
            return;
        }

        produtoData.inserir(produto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    public Produto consultarProduto(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido.");
            return null;
        }

        Produto produto = produtoData.buscarPorId(id);

        if  (produto == null) {
            System.out.println("Produto não encontrado.");
        }

        return produto;
    }

    public void excluirProduto(int id) {
        Produto produto = consultarProduto(id);
        if (produto != null) {
            produtoData.deletar(id);
            System.out.println("Produto removido com sucesso.");
        }
    }

    public void listarProdutos() {
        List<Produto> produtos = produtoData.listarTodos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    public void editarProduto(int id, double novoPreco) {
        Produto produto = consultarProduto(id);
        if (produto == null) return;

        if (novoPreco < 0) {
            System.out.println("Erro: Preço não pode ser negativo.");
            return;
        }

        produto.setPreco(novoPreco);
        produtoData.atualizar(produto);
        System.out.println("Produto atualizado: " + produto);
    }

    public void entradaEstoque(int id, int quantidade) {
        Produto produto = consultarProduto(id);
        if (produto == null) return;

        if (quantidade <= 0) {
            System.out.println("Erro: Quantidade deve ser maior que zero.");
            return;
        }

        produto.setQtdEstoque(produto.getQtdEstoque() + quantidade);
        produtoData.atualizar(produto);
        System.out.println("Entrada registrada. Estoque atual: " + produto.getQtdEstoque());
    }

    public boolean saidaEstoque(int id, int quantidade) {
        Produto produto = consultarProduto(id);
        if (produto == null) return false;

        if (quantidade <= 0) {
            System.out.println("Erro: Quantidade deve ser maior que zero.");
            return false;
        }

        if (produto.getQtdEstoque() < quantidade) {
            System.out.println("Erro: Estoque insuficiente.");
            return false;
        }

        produto.setQtdEstoque(produto.getQtdEstoque() - quantidade);
        produtoData.atualizar(produto);
        System.out.println("Saída registrada. Estoque atual: " + produto.getQtdEstoque());
        return true;
    }

    public void baixaEstoque(int id, int quantidade, String motivo) {
        Produto produto = consultarProduto(id);
        if (produto == null) return;

        if (motivo == null || motivo.isBlank()) {
            System.out.println("Erro: Motivo da baixa não pode ser vazio.");
            return;
        }

        if (quantidade <= 0) {
            System.out.println("Erro: Quantidade deve ser maior que zero.");
            return;
        }

        if (produto.getQtdEstoque() < quantidade) {
            System.out.println("Erro: Quantidade maior que estoque atual.");
            return;
        }

        produto.setQtdEstoque(produto.getQtdEstoque() - quantidade);
        produtoData.atualizar(produto);
        System.out.println("Baixa registrada. Motivo: " + motivo);
    }
}
