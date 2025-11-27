package angeloni.database;

import angeloni.produtos.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoData {

    // Inserir produto no banco
    public void inserir(Produto produto) {
        String sql = "INSERT INTO Produtos (IdProduto, NomeProduto, Preco, Qtd_Estoque) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getId());
            stmt.setString(2, produto.getNome());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getQtdEstoque());

            stmt.executeUpdate();
            System.out.println("Produto inserido no banco com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto.");
            e.printStackTrace();
        }
    }

    // Buscar produto por ID
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM Produtos WHERE IdProduto = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                        rs.getInt("IdProduto"),
                        rs.getString("NomeProduto"),
                        rs.getDouble("Preco"),
                        rs.getInt("Qtd_Estoque")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto.");
            e.printStackTrace();
        }

        return null;
    }

    // Listar todos os produtos
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produtos";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("IdProduto"),
                        rs.getString("NomeProduto"),
                        rs.getDouble("Preco"),
                        rs.getInt("Qtd_Estoque")
                );
                produtos.add(produto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos.");
            e.printStackTrace();
        }

        return produtos;
    }

    // Atualizar produto
    public void atualizar(Produto produto) {
        String sql = "UPDATE Produtos SET NomeProduto = ?, Preco = ?, Qtd_Estoque = ? WHERE IdProduto = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQtdEstoque());
            stmt.setInt(4, produto.getId());

            stmt.executeUpdate();
            System.out.println("Produto atualizado no banco.");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto.");
            e.printStackTrace();
        }
    }

    // Deletar produto
    public void deletar(int id) {
        String sql = "DELETE FROM Produtos WHERE IdProduto = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Produto deletado do banco.");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto.");
            e.printStackTrace();
        }
    }
}