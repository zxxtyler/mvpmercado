package clientes;


import java.util.ArrayList;
import java.util.List;


public class ClienteService {
    private List<Cliente> clientes = new ArrayList<>();


    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }


    public Cliente consultarCliente(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                return cliente;
            }
        }
        System.out.println("Cliente não encontrado.");
        return null;
    }


    public void excluirCliente(int id) {
        Cliente cliente = consultarCliente(id);
        if (cliente != null) {
            clientes.remove(cliente);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }


    public void listarClientes() {
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }
}





