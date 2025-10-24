package angeloni.vendas;

import angeloni.clientes.Categoria;

import java.util.ArrayList;
import java.util.List;

public class VendaService {
    private List<Venda> vendas = new ArrayList<>();

    public void registrarVenda(Venda venda) {
        vendas.add(venda);
    }

    public void verificarDesconto() {
        for (Venda venda : vendas) {
            
        }
    }

    public void definirDesconto() {
        for (Venda venda : vendas) {
            if (venda.ValorTotal() >= 50 && venda.ValorTotal() <= 74) {
                Categoria categoria = Categoria.BRONZE;
            } else if (venda.ValorTotal() >= 75 && venda.ValorTotal() <= 99) {
                Categoria categoria = Categoria.PRATA;
            } else if (venda.ValorTotal() >= 100) {
                Categoria categoria = Categoria.OURO;
            }
        }
    }



}
