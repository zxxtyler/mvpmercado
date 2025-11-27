package angeloni.vendas;

import angeloni.clientes.Categoria;
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

        int idVenda = vendaData.inserir(venda);
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
        }
    }

    public void definirDesconto() {
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        for (Venda venda : vendas) {
            double total = venda.ValorTotal();

            if (total >= 100) {
                venda.getCliente().setCategoria(Categoria.OURO);
            } else if (total >= 75) {
                venda.getCliente().setCategoria(Categoria.PRATA);
            } else if (total >= 50) {
                venda.getCliente().setCategoria(Categoria.BRONZE);
            } else {
                venda.getCliente().setCategoria(Categoria.NENHUMA);
            }
            clienteData.atualizar(venda.getCliente());
        }
    }
}
