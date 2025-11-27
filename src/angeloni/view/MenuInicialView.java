package angeloni.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuInicialView extends JFrame {

    private JPanel painel;

    public MenuInicialView() {
        setTitle("Sistema Mini Mercado Angeloni");
        setLayout(new FlowLayout());

        this.painel = new JPanel();
        this.painel.setLayout(new GridLayout(6, 1, 10, 10));
        this.painel.setPreferredSize(new Dimension(400, 500));
        add(this.painel);

        criarBotao("Cadastrar produto", new BotaoCadastrarHandler());
        criarBotao("Consultar produtos", new BotaoConsultarHandler());
        criarBotao("Editar produto", new BotaoEditarHandler());
        criarBotao("Excluir produto", new BotaoExcluirHandler());
        criarBotao("Relatórios", new BotaoRelatoriosHandler());
        criarBotao("Sair", new BotaoSairHandler());

        setSize(new Dimension(450, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void criarBotao(String label, ActionListener listener) {
        JButton botao = new JButton(label);
        botao.addActionListener(listener);
        botao.setPreferredSize(new Dimension(300, 60));
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        this.painel.add(botao);
    }

    private class BotaoCadastrarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new CadastroView();
        }
    }

    private class BotaoConsultarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ConsultaView();
        }
    }

    private class BotaoEditarHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new EdicaoView();
        }
    }

    private class BotaoExcluirHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idStr = JOptionPane.showInputDialog(null,
                    "Digite o ID do produto a excluir:",
                    "Excluir Produto",
                    JOptionPane.QUESTION_MESSAGE);

            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    angeloni.produtos.ProdutoService produtoService = new angeloni.produtos.ProdutoService();

                    if (produtoService.consultarProduto(id) != null) {
                        produtoService.excluirProduto(id);
                        JOptionPane.showMessageDialog(null,
                                "Produto excluído com sucesso.",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Produto não encontrado.",
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "ID inválido. Digite apenas números.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class BotaoRelatoriosHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RelatorioView();
        }
    }

    private class BotaoSairHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                System.exit(0);
        }
    }
}