package angeloni.database;

import angeloni.vendas.ItemVenda;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemVendaData {

    // Inserir item de venda no banco
    public void inserir(ItemVenda item, int idVenda) {
        String sql = "INSERT INTO Itens_Venda (IdVenda, Nome_Produto, Quantidade, Preco) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            stmt.setString(2, item.getNome());
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getPreco());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir item de venda!");
            e.printStackTrace();
        }
    }

    // Buscar itens por ID da venda
    public List<ItemVenda> buscarPorVenda(int idVenda) {
        List<ItemVenda> itens = new ArrayList<>();
        String sql = "SELECT * FROM Itens_Venda WHERE IdVenda = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ItemVenda item = new ItemVenda(
                        rs.getString("Nome_Produto"),
                        rs.getInt("Quantidade"),
                        rs.getDouble("Preco")
                );
                itens.add(item);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar itens de venda!");
            e.printStackTrace();
        }

        return itens;
    }

    // Deletar itens por ID da venda
    public void deletarPorVenda(int idVenda) {
        String sql = "DELETE FROM Itens_Venda WHERE IdVenda = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar itens de venda!");
            e.printStackTrace();
        }
    }
}