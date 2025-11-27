package angeloni.view;

import angeloni.produtos.Produto;
import angeloni.produtos.ProdutoService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroView extends JFrame {
    private JPanel painel;
    private JTextField tfId;
    private JTextField tfNome;
    private JTextField tfPreco;
    private JTextField tfEstoque;
    private ProdutoService produtoService;

    public CadastroView() {
        this.produtoService = new ProdutoService();

        setTitle("Cadastro de Produtos");
        setLayout(new FlowLayout());

        this.painel = new JPanel();
        this.painel.setLayout(new FlowLayout());
        this.painel.setPreferredSize(new Dimension(500, 550));
        add(painel);

        criarTextField("ID do Produto:", "tfId");
        criarTextField("Nome do Produto:", "tfNome");
        criarTextField("Preço (R$):", "tfPreco");
        criarTextField("Quantidade em Estoque:", "tfEstoque");

        criarBotao("Salvar", new BotaoSalvarHandler());
        criarBotao("Limpar", new BotaoLimparHandler());
        criarBotao("Voltar", new BotaoVoltarHandler());

        setSize(new Dimension(550, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setPreferredSize(new Dimension(450, 30));
        this.painel.add(label);
    }

    private void criarTextField(String label, String campo) {
        criarLabel(label);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(450, 35));
        this.painel.add(textField);

        switch(campo) {
            case "tfId": this.tfId = textField; break;
            case "tfNome": this.tfNome = textField; break;
            case "tfPreco": this.tfPreco = textField; break;
            case "tfEstoque": this.tfEstoque = textField; break;
        }
    }

    private void criarBotao(String label, ActionListener listener) {
        JButton botao = new JButton(label);
        botao.addActionListener(listener);
        botao.setPreferredSize(new Dimension(140, 50));
        this.painel.add(botao);
    }

    private void limparCampos() {
        tfId.setText("");
        tfNome.setText("");
        tfPreco.setText("");
        tfEstoque.setText("");
    }

    private void cadastrarProduto() {
        try {
            if (tfId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Erro: O campo ID não pode estar vazio.",
                        "Campo Obrigatório",
                        JOptionPane.ERROR_MESSAGE);
                tfId.requestFocus();
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

            if (tfEstoque.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Erro: O campo Estoque não pode estar vazio.",
                        "Campo Obrigatório",
                        JOptionPane.ERROR_MESSAGE);
                tfEstoque.requestFocus();
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

            String nome = tfNome.getText().trim();

            double preco;
            try {
                preco = Double.parseDouble(tfPreco.getText().trim().replace(",", "."));
                if (preco < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Erro: Preço não pode ser negativo.",
                            "Preço Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    tfPreco.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Preço deve ser um número válido. (Ex: 10.50)",
                        "Formato Inválido",
                        JOptionPane.ERROR_MESSAGE);
                tfPreco.requestFocus();
                return;
            }

            int estoque;
            try {
                estoque = Integer.parseInt(tfEstoque.getText().trim());
                if (estoque < 0) {
                    JOptionPane.showMessageDialog(this,
                            "Erro: Quantidade em estoque não pode ser negativa.",
                            "Estoque Inválido",
                            JOptionPane.ERROR_MESSAGE);
                    tfEstoque.requestFocus();
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Estoque deve ser um número inteiro válido.",
                        "Formato Inválido",
                        JOptionPane.ERROR_MESSAGE);
                tfEstoque.requestFocus();
                return;
            }

            if (produtoService.consultarProduto(id) != null) {
                JOptionPane.showMessageDialog(this,
                        "Erro: Já existe um produto cadastrado com o ID " + id + ".",
                        "ID Duplicado",
                        JOptionPane.ERROR_MESSAGE);
                tfId.requestFocus();
                return;
            }

            Produto produto = new Produto(id, nome, preco, estoque);
            produtoService.cadastrarProduto(produto);

            JOptionPane.showMessageDialog(this,
                    "Produto cadastrado com sucesso.\n\n" +
                            "ID: " + id + "\n" +
                            "Nome: " + nome + "\n" +
                            "Preço: R$ " + String.format("%.2f", preco) + "\n" +
                            "Estoque: " + estoque + " unidades",
                    "Cadastro Realizado",
                    JOptionPane.INFORMATION_MESSAGE);

            limparCampos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Erro inesperado ao cadastrar produto:\n" + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private class BotaoSalvarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cadastrarProduto();
        }
    }

    private class BotaoLimparHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            limparCampos();
        }
    }

    private class BotaoVoltarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }
}