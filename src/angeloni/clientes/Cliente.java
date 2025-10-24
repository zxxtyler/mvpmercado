package angeloni.clientes;

public abstract class Cliente {
    private int id;
    private String nome;
    private String telefone;
    private Categoria categoria;

    public Cliente(int id, String nome, String telefone, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id: " + id +
                ", Nome: '" + nome + '\'' +
                ", Telefone: '" + telefone + '\'' +
                ", Categoria: " + categoria +
                '}';
    }
}
