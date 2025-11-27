package angeloni.database;

import angeloni.vendas.Venda;
import angeloni.vendas.ItemVenda;
import angeloni.clientes.Cliente;
import angeloni.database.ClienteData;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaData {
    private ClienteData clienteData = new ClienteData();
    private ItemVendaData itemVendaData = new ItemVendaData();

    // Inserir venda no banco e RETORNAR o ID gerado
    public int inserir(Venda venda) {
        String sql = "INSERT INTO Vendas (IdCliente, Desconto, Valor_Total) VALUES (?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, venda.getCliente().getId());
            stmt.setDouble(2, venda.getDesconto());
            stmt.setDouble(3, venda.ValorTotal());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idVenda = rs.getInt(1);

                // Inserir os itens da venda
                for (ItemVenda item : venda.getItens()) {
                    itemVendaData.inserir(item, idVenda);
                }

                System.out.println("Venda inserida no banco com sucesso!");
                return idVenda; // RETORNA O ID
            }

        } catch (SQLException e) {
            System.err.println("Erro ao inserir venda!");
            e.printStackTrace();
        }

        return -1; 
    }

    // Buscar venda por ID
    public Venda buscarPorId(int id) {
        String sql = "SELECT * FROM Vendas WHERE IdVenda = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idCliente = rs.getInt("IdCliente");
                Cliente cliente = clienteData.buscarPorId(idCliente);

                double desconto = rs.getDouble("Desconto");

                // Buscar os itens da venda
                List<ItemVenda> itens = itemVendaData.buscarPorVenda(id);

                Venda venda = new Venda(cliente, itens, desconto);
                return venda;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda!");
            e.printStackTrace();
        }

        return null;
    }

    // Listar todas as vendas
    public List<Venda> listarTodas() {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT * FROM Vendas";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idVenda = rs.getInt("IdVenda");
                int idCliente = rs.getInt("IdCliente");
                Cliente cliente = clienteData.buscarPorId(idCliente);

                double desconto = rs.getDouble("Desconto");

                // Buscar os itens da venda
                List<ItemVenda> itens = itemVendaData.buscarPorVenda(idVenda);

                Venda venda = new Venda(cliente, itens, desconto);
                vendas.add(venda);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas!");
            e.printStackTrace();
        }

        return vendas;
    }

    // Atualizar venda
    public void atualizar(Venda venda, int idVenda) {
        String sql = "UPDATE Vendas SET IdCliente = ?, Desconto = ?, Valor_Total = ? WHERE IdVenda = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getCliente().getId());
            stmt.setDouble(2, venda.getDesconto());
            stmt.setDouble(3, venda.ValorTotal());
            stmt.setInt(4, idVenda);

            stmt.executeUpdate();
            System.out.println("Venda atualizada no banco!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar venda!");
            e.printStackTrace();
        }
    }

    // Deletar venda
    public void deletar(int id) {
        // Primeiro deletar os itens da venda
        itemVendaData.deletarPorVenda(id);

        String sql = "DELETE FROM Vendas WHERE IdVenda = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Venda deletada do banco!");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar venda!");
            e.printStackTrace();
        }
    }
}