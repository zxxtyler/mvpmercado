package angeloni.produtos;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int qtdEstoque;

    public Produto(int id, String nome, double preco, int qtdEstoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    @Override
    public String toString() {
        return "Produto - " +
                "Id: " + id +
                ", Nome: '" + nome + '\'' +
                ", Preco: " + preco +
                ", qtdEstoque: " + qtdEstoque;
    }
}
