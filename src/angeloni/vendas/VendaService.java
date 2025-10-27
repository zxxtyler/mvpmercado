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
            Categoria categoria = venda.getCliente().getCategoria();
            double desconto = 0.0;

            switch (categoria) {
                case BRONZE -> {
                    desconto = venda.ValorTotal() * 0.10;
                }
                case PRATA -> {
                    desconto = venda.ValorTotal() * 0.15;
                }
                case OURO -> {
                    desconto = venda.ValorTotal() * 0.20;
                }
            }
            }
    }

    public void definirDesconto() {
        for (Venda venda : vendas) {
            if (venda.ValorTotal() >= 50 && venda.ValorTotal() <= 74) {
                venda.getCliente().setCategoria(Categoria.BRONZE);
            } else if (venda.ValorTotal() >= 75 && venda.ValorTotal() <= 99) {
                venda.getCliente().setCategoria(Categoria.PRATA);
            } else if (venda.ValorTotal() >= 100) {
                venda.getCliente().setCategoria(Categoria.OURO);
            }
        }
    }

}
