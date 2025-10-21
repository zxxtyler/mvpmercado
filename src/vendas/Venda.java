package vendas;

import clientes.Cliente;

public class Venda {
    private Cliente cliente;
    private ItemVenda itens;
    private double desconto;
    private double valorTotal;

    public Venda(Cliente cliente, ItemVenda itens, double desconto, double valorTotal) {
        this.cliente = cliente;
        this.itens = itens;
        this.desconto = desconto;
        this.valorTotal = valorTotal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ItemVenda getItens() {
        return itens;
    }

    public void setItens(ItemVenda itens) {
        this.itens = itens;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "Cliente: " + cliente +
                ", Itens: " + itens +
                ", Desconto: " + desconto +
                ", Valor Total: " + valorTotal +
                '}';
    }
}
