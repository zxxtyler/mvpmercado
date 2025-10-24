package angeloni.clientes;

public class ClientePessoaFisica extends Cliente {
    private String cpf;

    public ClientePessoaFisica(int id, String nome, String telefone, Categoria categoria) {
        super(id, nome, telefone, categoria);
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
