package angeloni;


import angeloni.clientes.*;
import angeloni.produtos.*;
import angeloni.vendas.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Instanciando os serviços
        ClienteService clienteService = new ClienteService();
        ProdutoService produtoService = new ProdutoService();
        VendaService vendaService = new VendaService();


        System.out.println("===== GERENCIAMENTO DE CLIENTES =====");


        // Cadastro de clientes
        // Todos os clientes têm a categoria nenhuma por padrão, ela é definida após o cálculo da compra
        ClientePessoaFisica cliente1 = new ClientePessoaFisica(1, "Maria Silva", "(48) 99999-1111", Categoria.NENHUMA);
        cliente1.setCpf("123.456.789-00");


        ClientePessoaFisica cliente2 = new ClientePessoaFisica(2, "João Santos", "(48) 99999-2222", Categoria.NENHUMA);


        ClientePessoaJuridica cliente3 = new ClientePessoaJuridica(3, "Empresa ABC", "(48) 3333-4444", Categoria.NENHUMA);
        cliente3.setCnpj("12.345.678/0001-99");


        ClientePessoaFisica cliente4 = new ClientePessoaFisica(4, "Ana Costa", "(48) 99999-5555", Categoria.NENHUMA);
        cliente4.setCpf("111.222.333-44");


        ClientePessoaFisica cliente5 = new ClientePessoaFisica(5, "José Lima", "(48 99999-99999", Categoria.NENHUMA);


        clienteService.cadastrarCliente(cliente1);
        clienteService.cadastrarCliente(cliente2);
        clienteService.cadastrarCliente(cliente3);
        clienteService.cadastrarCliente(cliente4);
        clienteService.cadastrarCliente(cliente5);


        // Função de listar clientes
        System.out.println("- Listando todos os Clientes -");
        clienteService.listarClientes();
        System.out.println();


        // Função de consultar um cliente
        System.out.println("- Consultando Cliente Id 2 -");
        Cliente clienteConsultado = clienteService.consultarCliente(2);
        System.out.println(clienteConsultado);
        System.out.println();


        // Função de editar um cliente
        System.out.println("- Editando Cliente Id 1 -");
        clienteService.editarCliente(1, "(48) 98888-7777", Categoria.OURO);
        System.out.println();


        // Função de excluir um cliente
        System.out.println("- Excluindo Cliente ID 4 -");
        clienteService.excluirCliente(4);
        System.out.println("Cliente excluído.\n");


        System.out.println("- Lista atualizada de Clientes -");
        clienteService.listarClientes();
        System.out.println();


        System.out.println("===== GERENCIAMENTO DE PRODUTOS =====");


        // Cadastro de produtos
        Produto produto1 = new Produto(1, "Arroz 5kg", 25.90, 0);
        Produto produto2 = new Produto(2, "Feijão 1kg", 8.50, 0);
        Produto produto3 = new Produto(3, "Açúcar 1kg", 4.20, 0);
        Produto produto4 = new Produto(4, "Café 500g", 12.80, 0);
        Produto produto5 = new Produto(5, "Leite 1L", 5.30, 0);


        produtoService.cadastrarProduto(produto1);
        produtoService.cadastrarProduto(produto2);
        produtoService.cadastrarProduto(produto3);
        produtoService.cadastrarProduto(produto4);
        produtoService.cadastrarProduto(produto5);


        // Função de listar produtos
        System.out.println("- Listando todos os Produtos -");
        produtoService.listarProdutos();
        System.out.println();


        // Função de consultar os produtos
        System.out.println("- Consultando Produto Id 3 -");
        Produto produtoConsultado = produtoService.consultarProduto(3);
        System.out.println(produtoConsultado);
        System.out.println();


        // Função de editar um produto
        System.out.println("- Editando Produto Id 2 -");
        produtoService.editarProduto(2, 9.50);
        System.out.println();


        // Gerenciamento de estoque
        // Função de entrada de produtos
        System.out.println("- Registrando Entrada de Estoque -");
        produtoService.entradaEstoque(1, 100); // 100 unidades de arroz
        produtoService.entradaEstoque(2, 80);  // 80 unidades de feijão
        produtoService.entradaEstoque(3, 120); // 120 unidades de açúcar
        produtoService.entradaEstoque(4, 50);  // 50 unidades de café
        produtoService.entradaEstoque(5, 200); // 200 unidades de leite
        System.out.println();


        // Função de saída de produtos
        System.out.println("- Registrando Saída de Estoque -");
        produtoService.saidaEstoque(1, 10); // Saída de 10 unidades de arroz
        produtoService.saidaEstoque(3, 15); // Saída de 15 unidades de açúcar
        System.out.println();


        // Função de baixa de estoque
        System.out.println("- Registrando Baixa de Estoque -");
        produtoService.baixaEstoque(2, 5, "Produto vencido");
        produtoService.baixaEstoque(5, 10, "Embalagem danificada");
        System.out.println();


        // Função de excluir um produto
        System.out.println("- Excluindo Produto Id 4 -");
        produtoService.excluirProduto(4);
        System.out.println("Produto excluído.\n");


        System.out.println("- Lista atualizada de Produtos -");
        produtoService.listarProdutos();
        System.out.println();


        System.out.println("======= GERENCIAMENTO DE VENDAS =======");


        // Criando e registrando as vendas
        System.out.println("- Registrando Venda 1 (Maria Silva) -");
        List<ItemVenda> itensVenda1 = new ArrayList<>();
        itensVenda1.add(new ItemVenda("Arroz 5kg", 2, 25.90));
        itensVenda1.add(new ItemVenda("Feijão 1kg", 3, 8.50));
        itensVenda1.add(new ItemVenda("Açúcar 1kg", 1, 4.20));


        Venda venda1 = new Venda(cliente1, itensVenda1);
        vendaService.registrarVenda(venda1);
        System.out.println(venda1);
        System.out.println();


        // Criando e registrando a segunda venda
        System.out.println("- Registrando Venda 2 (João Santos) -");
        List<ItemVenda> itensVenda2 = new ArrayList<>();
        itensVenda2.add(new ItemVenda("Leite 1L", 5, 5.30));
        itensVenda2.add(new ItemVenda("Açúcar 1kg", 2, 4.20));


        Venda venda2 = new Venda(cliente2, itensVenda2);
        vendaService.registrarVenda(venda2);
        System.out.println(venda2);
        System.out.println();


        // Criando e registrando a terceira venda
        System.out.println("- Registrando Venda 3 (Empresa ABC) -");
        List<ItemVenda> itensVenda3 = new ArrayList<>();
        itensVenda3.add(new ItemVenda("Arroz 5kg", 10, 25.90));
        itensVenda3.add(new ItemVenda("Feijão 1kg", 8, 9.50));
        itensVenda3.add(new ItemVenda("Leite 1L", 20, 5.30));


        Venda venda3 = new Venda(cliente3, itensVenda3); // Com R$50 de desconto
        vendaService.registrarVenda(venda3);
        System.out.println(venda3);
        System.out.println();


        //Criando e registrando a quarta venda
        System.out.println("- Registrando Venda 4 (José Lima) -");
        List<ItemVenda> itensVenda4 = new ArrayList<>();
        itensVenda4.add(new ItemVenda("Arroz 5kg", 2, 25.90));
        itensVenda4.add(new ItemVenda("Feijão 1kg", 1, 8.50));


        Venda venda4 = new Venda(cliente5, itensVenda4);
        vendaService.registrarVenda(venda4);
        System.out.println(venda4);
        System.out.println();


        // Função de adicionar um item à uma venda
        System.out.println("- Adicionando item à Venda 1 -");
        ItemVenda novoItem = new ItemVenda("Leite 1L", 2, 5.30);
        venda1.adicionarItem(novoItem);
        System.out.println("Item adicionado.");
        System.out.println(venda1);
        System.out.println();


        // Função de remoção de item de uma venda
        System.out.println("- Removendo item da Venda 2 -");
        venda2.removerItem(itensVenda2.get(0)); // Remove o primeiro item
        System.out.println("Item removido.");
        System.out.println(venda2);
        System.out.println();


        // Função para definir a categoria do desconto pelo valor da venda
        System.out.println("- Redefinindo Categoria dos Clientes por valor de compra -");
        vendaService.definirDesconto();
        System.out.println("Categorias atualizadas.\n");


        System.out.println("- Clientes com categorias atualizadas -");
        clienteService.listarClientes();
        System.out.println();


        // Função para verificar os descontos
        System.out.println("- Aplicando Desconto por Categoria -");
        vendaService.verificarDesconto();
        System.out.println("Descontos aplicados!\n");


        System.out.println("- Vendas com desconto aplicado -");
        System.out.println(venda1);
        System.out.println(venda2);
        System.out.println(venda3);
        System.out.println(venda4);
        System.out.println();


        System.out.println("======== SISTEMA FINALIZADO ========");
    }
}

