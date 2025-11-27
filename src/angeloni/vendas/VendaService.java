package angeloni.vendas;

import angeloni.clientes.Categoria;
import angeloni.clientes.Cliente;
import angeloni.database.VendaData;
import angeloni.database.ClienteData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendaService {
    private List<Venda> vendas = new ArrayList<>();
    private Map<Venda, Integer> vendasIds = new HashMap<>(); 
    private VendaData vendaData = new VendaData();
    private ClienteData clienteData = new ClienteData();
    private int ultimoIdVenda = 0;

    public void registrarVenda(Venda venda) {
        if (venda == null) {
            System.out.println("Erro: Venda não pode ser nula.");
            return;
        }

        if (venda.getCliente() == null) {
            System.out.println("Erro: Venda sem cliente.");
            return;
        }

        if (venda.getItens().isEmpty()) {
            System.out.println("Erro: Venda deve conter itens.");
            return;
        }

        vendas.add(venda);
        int idVenda = vendaData.inserir(venda); 
        vendasIds.put(venda, idVenda);

        System.out.println("Venda registrada com sucesso!");
    }

    public void verificarDesconto() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        for (Venda venda : vendas) {
            Categoria categoria = venda.getCliente().getCategoria();
            double desconto = switch (categoria) {
                case BRONZE -> venda.ValorTotal() * 0.10;
                case PRATA -> venda.ValorTotal() * 0.15;
                case OURO -> venda.ValorTotal() * 0.20;
                default -> 0;
            };

            venda.setDesconto(desconto);

        
            Integer idVenda = vendasIds.get(venda);
            if (idVenda != null) {
                vendaData.atualizar(venda, idVenda);
            }
        }
    }

    public void definirDesconto() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        for (Venda venda : vendas) {
            double total = venda.ValorTotal();
            Cliente cliente = venda.getCliente();

            if (total >= 100) {
                cliente.setCategoria(Categoria.OURO);
            } else if (total >= 75) {
                cliente.setCategoria(Categoria.PRATA);
            } else if (total >= 50) {
                cliente.setCategoria(Categoria.BRONZE);
            } else {
                cliente.setCategoria(Categoria.NENHUMA);
            }

            clienteData.atualizar(cliente);
        }
    }
}