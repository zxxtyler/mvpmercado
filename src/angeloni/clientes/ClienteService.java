package angeloni.clientes;

import angeloni.database.ClienteData;
import java.util.List;

public class ClienteService {
    private ClienteData clienteData = new ClienteData();

    public void cadastrarCliente(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Erro: Cliente não pode ser nulo.");
            return;
        }
        if (cliente.getId() <= 0) {
            System.out.println("Erro: ID inválido.");
            return;
        }

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            System.out.println("Erro: Nome não pode ser vazio.");
            return;
        }

        if (consultarCliente(cliente.getId()) != null) {
            System.out.println("Erro: Já existe cliente com esse ID.");
            return;
        }

        clienteData.inserir(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    public Cliente consultarCliente(int id) {
        if (id <= 0) {
            System.out.println("Erro: ID inválido.");
            return null;
        }

        Cliente cliente = clienteData.buscarPorId(id);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
        }

        return cliente;
    }

    public void excluirCliente(int id) {
        Cliente cliente = consultarCliente(id);
        if (cliente != null) {
            clienteData.deletar(id);
            System.out.println("Cliente removido com sucesso.");
        }
    }

    public void listarClientes() {
        List<Cliente> clientes = clienteData.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    public void editarCliente(int id, String novoTelefone, Categoria novaCategoria) {
        Cliente cliente = consultarCliente(id);
        if (cliente == null) return;

        if (novoTelefone == null || novoTelefone.isBlank()) {
            System.out.println("Erro: Telefone inválido.");
            return;
        }

        cliente.setTelefone(novoTelefone);
        cliente.setCategoria(novaCategoria);
        clienteData.atualizar(cliente);

        System.out.println("Cliente atualizado: " + cliente);
    }
}
