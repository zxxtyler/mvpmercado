package angeloni.view;

import angeloni.produtos.Produto;
import angeloni.produtos.ProdutoService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ConsultaView extends JFrame {
    private JPanel painelSuperior;
    private JPanel painelInferior;
    private JTextField tfId;
    private JTextArea taResultado;
    private JScrollPane scrollPane;
    private ProdutoService produtoService;

    public ConsultaView() {
        this.produtoService = new ProdutoService();

        setTitle("Consulta de Produtos");
        setLayout(new BorderLayout());

        this.painelSuperior = new JPanel();
        this.painelSuperior.setLayout(new FlowLayout());
        this.painelSuperior.setPreferredSize(new Dimension(600, 100));
        add(this.painelSuperior, BorderLayout.NORTH);

        criarCampoBusca();

        this.painelInferior = new JPanel();
        this.painelInferior.setLayout(new BorderLayout());
        add(this.painelInferior, BorderLayout.CENTER);

        criarAreaResultado();
        criarBotoesInferiores();

        setSize(new Dimension(650, 550));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void criarCampoBusca() {
        JLabel label = new JLabel("ID do Produto:");
        label.setFont(new Font("Arial", Font.BOLD, 13));
        this.painelSuperior.add(label);

        this.tfId = new JTextField();
        this.tfId.setPreferredSize(new Dimension(150, 30));
        this.painelSuperior.add(this.tfId);

        JButton btnBuscar = new JButton("Buscar por ID");
        btnBuscar.setPreferredSize(new Dimension(140, 35));
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 11));
        btnBuscar.addActionListener(new BotaoBuscarHandler());
        this.painelSuperior.add(btnBuscar);

        JButton btnListarTodos = new JButton("Listar Todos");
        btnListarTodos.setPreferredSize(new Dimension(140, 35));
        btnListarTodos.setFont(new Font("Arial", Font.BOLD, 11));
        btnListarTodos.addActionListener(new BotaoListarTodosHandler());
        this.painelSuperior.add(btnListarTodos);
    }

    private void criarAreaResultado() {
        this.taResultado = new JTextArea();
        this.taResultado.setEditable(false);
        this.taResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));
        this.taResultado.setMargin(new Insets(10, 10, 10, 10));

        this.scrollPane = new JScrollPane(this.taResultado);
        this.scrollPane.setPreferredSize(new Dimension(600, 350));
        this.painelInferior.add(this.scrollPane, BorderLayout.CENTER);
    }

    private void criarBotoesInferiores() {
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout());
        painelBotoes.setPreferredSize(new Dimension(600, 60));

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setPreferredSize(new Dimension(120, 40));
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpar.addActionListener(new BotaoLimparHandler());
        painelBotoes.add(btnLimpar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(120, 40));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.addActionListener(new BotaoVoltarHandler());
        painelBotoes.add(btnVoltar);

        this.painelInferior.add(painelBotoes, BorderLayout.SOUTH);
    }

    private void buscarPorId() {
        try {
            if (tfId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Digite o ID do produto para buscar.",
                        "Campo Obrigatório",
                        JOptionPane.WARNING_MESSAGE);
                tfId.requestFocus();
                return;
            }

            int id;
            try {
                id = Integer.parseInt(tfId.getText().trim());
                if (id <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Erro: ID deve ser um número positivo.",
                            "ID Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    tfId.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Erro: ID deve ser um número inteiro válido.",
                        "Formato Inválido",
                        JOptionPane.ERROR_MESSAGE);
                tfId.requestFocus();
                return;
            }

            Produto produto = produtoService.consultarProduto(id);

            if (produto == null) {
                taResultado.setText("Produto não encontrado.\n\nID pesquisado: " + id);
                JOptionPane.showMessageDialog(this,
                        "Nenhum produto encontrado com o ID " + id,
                        "Produto Não Encontrado",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("══════════════════════════════════════════════════════════════════════\n");
            sb.append("                   PRODUTO ENCONTRADO                  \n");
            sb.append("══════════════════════════════════════════════════════════════════════\n");
            sb.append(String.format("ID:               %d\n", produto.getId()));
            sb.append(String.format("Nome:             %s\n", produto.getNome()));
            sb.append(String.format("Preço:            R$ %.2f\n", produto.getPreco()));
            sb.append(String.format("Qtd. Estoque:     %d unidades\n", produto.getQtdEstoque()));

            taResultado.setText(sb.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao buscar produto:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void listarTodos() {
        try {
            List<Produto> produtos = produtoService.consultarProduto(0) != null ?
                    java.util.Arrays.asList(produtoService.consultarProduto(0)) :
                    new java.util.ArrayList<>();


            angeloni.database.ProdutoData produtoData = new angeloni.database.ProdutoData();
            produtos = produtoData.listarTodos();

            if (produtos.isEmpty()) {
                taResultado.setText("Nenhum produto cadastrado no sistema.\n\n" +
                        "Cadastre produtos usando a opção 'Cadastrar Produto' no menu principal.");
                JOptionPane.showMessageDialog(this,
                        "Não há produtos cadastrados no sistema.",
                        "Lista Vazia",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════════════════════════════════\n");
            sb.append("                          LISTA DE PRODUTOS                                \n");
            sb.append("═══════════════════════════════════════════════════════════════════════════\n\n");
            sb.append(String.format("Total de produtos cadastrados: %d\n\n", produtos.size()));

            for (Produto produto : produtos) {
                sb.append(String.format("ID: %-5d | Nome: %-30s | Preço: R$ %8.2f | Estoque: %4d\n",
                        produto.getId(),
                        produto.getNome().length() > 30 ? produto.getNome().substring(0, 27) + "..." : produto.getNome(),
                        produto.getPreco(),
                        produto.getQtdEstoque()));
            }

            taResultado.setText(sb.toString());
            taResultado.setCaretPosition(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao listar produtos:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            taResultado.setText("Erro ao carregar produtos.");
            e.printStackTrace();
        }
    }

    private class BotaoBuscarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buscarPorId();
        }
    }

    private class BotaoListarTodosHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            listarTodos();
        }
    }

    private class BotaoLimparHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tfId.setText("");
            taResultado.setText("");
            tfId.requestFocus();
        }
    }

    private class BotaoVoltarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}