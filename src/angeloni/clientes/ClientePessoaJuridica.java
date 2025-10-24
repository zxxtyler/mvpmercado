package angeloni.clientes;

public class ClientePessoaJuridica extends Cliente{
    private String cnpj;

    public ClientePessoaJuridica(int id, String nome, String telefone, Categoria categoria) {
        super(id, nome, telefone, categoria);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
