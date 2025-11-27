package angeloni.view;

import angeloni.produtos.Produto;
import angeloni.produtos.ProdutoService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EdicaoView extends JFrame {
    private JPanel painel;
    private JTextField tfIdBusca;
    private JTextField tfNome;
    private JTextField tfPreco;
    private JButton btnSalvar;
    private ProdutoService produtoService;
    private Produto produtoAtual;

    public EdicaoView() {
        this.produtoService = new ProdutoService();

        setTitle("Edição de Produtos");
        setLayout(new FlowLayout());

        this.painel = new JPanel();
        this.painel.setLayout(new FlowLayout());
        this.painel.setPreferredSize(new Dimension(500, 450));
        add(painel);

        criarCampoBusca();
        criarCamposEdicao();
        criarBotoes();

        setSize(new Dimension(550, 500));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setPreferredSize(new Dimension(450, 30));
        label.setFont(new Font("Arial", Font.BOLD, 13));
        this.painel.add(label);
    }

    private void criarCampoBusca() {
        criarLabel("Digite o ID do Produto:");

        this.tfIdBusca = new JTextField();
        this.tfIdBusca.setPreferredSize(new Dimension(300, 30));
        this.painel.add(this.tfIdBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setPreferredSize(new Dimension(130, 30));
        btnBuscar.addActionListener(new BotaoBuscarHandler());
        this.painel.add(btnBuscar);
    }

    private void criarCamposEdicao() {
        criarLabel("Novo Nome:");
        this.tfNome = new JTextField();
        this.tfNome.setPreferredSize(new Dimension(450, 30));
        this.tfNome.setEnabled(false);
        this.painel.add(this.tfNome);

        criarLabel("Novo Preço (R$):");
        this.tfPreco = new JTextField();
        this.tfPreco.setPreferredSize(new Dimension(450, 30));
        this.tfPreco.setEnabled(false);
        this.painel.add(this.tfPreco);
    }

    private void criarBotoes() {
        this.btnSalvar = new JButton("Salvar Alterações");
        this.btnSalvar.setPreferredSize(new Dimension(180, 45));
        this.btnSalvar.setEnabled(false);
        this.btnSalvar.addActionListener(new BotaoSalvarHandler());
        this.painel.add(this.btnSalvar);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(130, 45));
        btnVoltar.addActionListener(new BotaoVoltarHandler());
        this.painel.add(btnVoltar);
    }

    private void limparCampos() {
        tfNome.setText("");
        tfPreco.setText("");
        tfNome.setEnabled(false);
        tfPreco.setEnabled(false);
        btnSalvar.setEnabled(false);
        produtoAtual = null;
    }

    private void buscarProduto() {
        try {
            if (tfIdBusca.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Digite o ID do produto.",
                        "Campo Obrigatório",
                        JOptionPane.WARNING_MESSAGE);
                tfIdBusca.requestFocus();
                return;
            }

            int id;
            try {
                id = Integer.parseInt(tfIdBusca.getText().trim());
                if (id <= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Erro: ID deve ser um número positivo.",
                            "ID Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    tfIdBusca.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Erro: ID deve ser um número válido.",
                        "Formato Inválido",
                        JOptionPane.ERROR_MESSAGE);
                tfIdBusca.requestFocus();
                return;
            }

            Produto produto = produtoService.consultarProduto(id);

            if (produto == null) {
                limparCampos();
                JOptionPane.showMessageDialog(this,
                        "Produto não encontrado com o ID " + id,
                        "Produto Não Encontrado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            this.produtoAtual = produto;
            tfNome.setText(produto.getNome());
            tfPreco.setText(String.format("%.2f", produto.getPreco()));
            tfNome.setEnabled(true);
            tfPreco.setEnabled(true);
            btnSalvar.setEnabled(true);
            tfNome.requestFocus();


        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao buscar produto:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void salvarAlteracoes() {
        try {
            if (produtoAtual == null) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Busque um produto antes de salvar.",
                        "Nenhum Produto Selecionado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (tfNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Erro: O campo Nome não pode estar vazio.",
                        "Campo Obrigatório",
                        JOptionPane.ERROR_MESSAGE);
                tfNome.requestFocus();
                return;
            }

            if (tfPreco.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Erro: O campo Preço não pode estar vazio.",
                        "Campo Obrigatório",
                        JOptionPane.ERROR_MESSAGE);
                tfPreco.requestFocus();
                return;
            }

            String novoNome = tfNome.getText().trim();

            double novoPreco;
            try {
                novoPreco = Double.parseDouble(tfPreco.getText().trim().replace(",", "."));
                if (novoPreco < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Erro: Preço não pode ser negativo.",
                            "Preço Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    tfPreco.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Preço deve ser um número válido.",
                        "Formato Inválido",
                        JOptionPane.ERROR_MESSAGE);
                tfPreco.requestFocus();
                return;
            }

            produtoAtual.setNome(novoNome);
            produtoAtual.setPreco(novoPreco);

            angeloni.database.ProdutoData produtoData = new angeloni.database.ProdutoData();
            produtoData.atualizar(produtoAtual);

            JOptionPane.showMessageDialog(this,
                    "Produto atualizado com sucesso.\n\n" +
                            "ID: " + produtoAtual.getId() + "\n" +
                            "Nome: " + novoNome + "\n" +
                            "Preço: R$ " + String.format("%.2f", novoPreco),
                    "Alteração Realizada",
                    JOptionPane.INFORMATION_MESSAGE);

            limparCampos();
            tfIdBusca.setText("");
            tfIdBusca.requestFocus();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao salvar alterações:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private class BotaoBuscarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buscarProduto();
        }
    }

    private class BotaoSalvarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            salvarAlteracoes();
        }
    }

    private class BotaoVoltarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}