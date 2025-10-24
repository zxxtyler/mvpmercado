package angeloni.vendas;

import angeloni.clientes.Cliente;

import java.util.ArrayList;
import java.util.List;

public class Venda {
    private Cliente cliente;
    private List<ItemVenda> itens;
    private double desconto;

    public Venda(Cliente cliente, List<ItemVenda> itens, double desconto) {
        this.cliente = cliente;
        this.itens = new ArrayList<>();
        this.desconto = desconto;
    }

    public Venda(Cliente cliente, List<ItemVenda> itens) {
        this(cliente, itens, 0.0);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
    }

    public void removerItem(ItemVenda item) {
        itens.remove(item);
    }

    public double ValorTotal() {
        double total = 0;
        for (ItemVenda item : itens) {
            total += item.getPreco() * item.getQuantidade();
        }
        return total - desconto;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "Cliente: " + cliente +
                ", Itens: " + itens +
                ", Desconto: " + desconto +
                ", Valor Total: " + ValorTotal() +
                '}';
    }
}
