package angeloni.view;

import angeloni.produtos.Produto;
import angeloni.database.ProdutoData;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RelatorioView extends JFrame {
    private JPanel painelBotoes;
    private JTextArea taRelatorio;
    private JScrollPane scrollPane;
    private ProdutoData produtoData;

    public RelatorioView() {
        this.produtoData = new ProdutoData();

        setTitle("Relatórios");
        setLayout(new BorderLayout());

        criarPainelBotoes();
        criarAreaRelatorio();

        setSize(new Dimension(700, 550));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void criarPainelBotoes() {
        this.painelBotoes = new JPanel();
        this.painelBotoes.setLayout(new FlowLayout());
        this.painelBotoes.setPreferredSize(new Dimension(700, 80));
        add(this.painelBotoes, BorderLayout.NORTH);

        JButton btnEstoque = new JButton("Relatório de Estoque");
        btnEstoque.setPreferredSize(new Dimension(200, 50));
        btnEstoque.setFont(new Font("Arial", Font.BOLD, 12));
        btnEstoque.addActionListener(new BotaoEstoqueHandler());
        this.painelBotoes.add(btnEstoque);

        JButton btnCatalogo = new JButton("Catálogo de Produtos");
        btnCatalogo.setPreferredSize(new Dimension(200, 50));
        btnCatalogo.setFont(new Font("Arial", Font.BOLD, 12));
        btnCatalogo.addActionListener(new BotaoCatalogoHandler());
        this.painelBotoes.add(btnCatalogo);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(120, 50));
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.addActionListener(new BotaoVoltarHandler());
        this.painelBotoes.add(btnVoltar);
    }

    private void criarAreaRelatorio() {
        this.taRelatorio = new JTextArea();
        this.taRelatorio.setEditable(false);
        this.taRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        this.taRelatorio.setMargin(new Insets(10, 10, 10, 10));
        this.taRelatorio.setText("Selecione um relatório acima para visualizar.");

        this.scrollPane = new JScrollPane(this.taRelatorio);
        add(this.scrollPane, BorderLayout.CENTER);
    }

    private void gerarRelatorioEstoque() {
        try {
            List<Produto> produtos = produtoData.listarTodos();

            if (produtos.isEmpty()) {
                taRelatorio.setText("Nenhum produto cadastrado no sistema.");
                JOptionPane.showMessageDialog(this,
                        "Não há produtos cadastrados no sistema.",
                        "Lista Vazia",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════════════════════════════\n");
            sb.append("                      RELATÓRIO DE ESTOQUE                             \n");
            sb.append("═══════════════════════════════════════════════════════════════════════\n");
            sb.append("Produtos com quantidade em estoque\n");
            sb.append("Total de produtos: ").append(produtos.size()).append("\n");
            sb.append("───────────────────────────────────────────────────────────────────────\n\n");

            for (Produto produto : produtos) {
                sb.append(String.format("ID: %-5d\n", produto.getId()));
                sb.append(String.format("Nome: %s\n", produto.getNome()));
                sb.append(String.format("Quantidade em Estoque: %d unidades\n", produto.getQtdEstoque()));
                sb.append("\n");
            }

            taRelatorio.setText(sb.toString());
            taRelatorio.setCaretPosition(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar relatório de estoque:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            taRelatorio.setText("Erro ao carregar relatório.");
            e.printStackTrace();
        }
    }

    private void gerarCatalogoProdutos() {
        try {
            List<Produto> produtos = produtoData.listarTodos();

            if (produtos.isEmpty()) {
                taRelatorio.setText("Nenhum produto cadastrado no sistema.");
                JOptionPane.showMessageDialog(this,
                        "Não há produtos cadastrados no sistema.",
                        "Lista Vazia",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════════════════════════════════\n");
            sb.append("                      CATÁLOGO DE PRODUTOS                             \n");
            sb.append("═══════════════════════════════════════════════════════════════════════\n");
            sb.append("Produtos em estoque com preço\n");
            sb.append("Total de produtos: ").append(produtos.size()).append("\n");

            for (Produto produto : produtos) {
                sb.append(String.format("ID: %-5d\n", produto.getId()));
                sb.append(String.format("Nome: %s\n", produto.getNome()));
                sb.append(String.format("Preço: R$ %.2f\n", produto.getPreco()));
                sb.append("\n");
            }


            taRelatorio.setText(sb.toString());
            taRelatorio.setCaretPosition(0);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao gerar catálogo de produtos:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            taRelatorio.setText("Erro ao carregar catálogo.");
            e.printStackTrace();
        }
    }

    private class BotaoEstoqueHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gerarRelatorioEstoque();
        }
    }

    private class BotaoCatalogoHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gerarCatalogoProdutos();
        }
    }

    private class BotaoVoltarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}