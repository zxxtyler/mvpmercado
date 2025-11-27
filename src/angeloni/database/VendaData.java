package angeloni.database;

import angeloni.vendas.Venda;
import angeloni.vendas.ItemVenda;
import angeloni.clientes.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaData {

    // Inserir venda no banco (com transação para garantir consistência)
    public int inserir(Venda venda) {
        String sqlVenda = "INSERT INTO Vendas (IdCliente, Desconto, Valor_Total) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO Itens_Venda (IdVenda, Nome_Produto, Quantidade, Preco) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        int idVendaGerado = -1;

        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false); // Inicia transação

            // Inserir a venda
            PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
            stmtVenda.setInt(1, venda.getCliente().getId());
            stmtVenda.setDouble(2, venda.getDesconto());
            stmtVenda.setDouble(3, venda.ValorTotal());
            stmtVenda.executeUpdate();

            // Recuperar o ID gerado
            ResultSet rs = stmtVenda.getGeneratedKeys();
            if (rs.next()) {
                idVendaGerado = rs.getInt(1);
            }

            // Inserir os itens da venda
            PreparedStatement stmtItem = conn.prepareStatement(sqlItem);
            for (ItemVenda item : venda.getItens()) {
                stmtItem.setInt(1, idVendaGerado);
                stmtItem.setString(2, item.getNome());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.setDouble(4, item.getPreco());
                stmtItem.executeUpdate();
            }

            conn.commit(); // Confirma a transação
            System.out.println("Venda inserida no banco com sucesso. ID: " + idVendaGerado);

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Reverte em caso de erro
                    System.err.println("Transação revertida devido a erro.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("Erro ao inserir venda.");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return idVendaGerado;
    }

    // Buscar venda por ID (com seus itens)
    public Venda buscarPorId(int idVenda, ClienteData clienteData) {
        String sqlVenda = "SELECT * FROM Vendas WHERE IdVenda = ?";
        String sqlItens = "SELECT * FROM Itens_Venda WHERE IdVenda = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda);
             PreparedStatement stmtItens = conn.prepareStatement(sqlItens)) {

            // Buscar dados da venda
            stmtVenda.setInt(1, idVenda);
            ResultSet rsVenda = stmtVenda.executeQuery();

            if (rsVenda.next()) {
                int idCliente = rsVenda.getInt("IdCliente");
                double desconto = rsVenda.getDouble("Desconto");

                // Buscar o cliente
                Cliente cliente = clienteData.buscarPorId(idCliente);

                // Buscar os itens da venda
                stmtItens.setInt(1, idVenda);
                ResultSet rsItens = stmtItens.executeQuery();

                List<ItemVenda> itens = new ArrayList<>();
                while (rsItens.next()) {
                    ItemVenda item = new ItemVenda(
                            rsItens.getString("Nome_Produto"),
                            rsItens.getInt("Quantidade"),
                            rsItens.getDouble("Preco")
                    );
                    itens.add(item);
                }

                return new Venda(cliente, itens, desconto);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar venda.");
            e.printStackTrace();
        }

        return null;
    }

    // Listar todas as vendas
    public List<Venda> listarTodas(ClienteData clienteData) {
        List<Venda> vendas = new ArrayList<>();
        String sqlVendas = "SELECT IdVenda FROM Vendas";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlVendas)) {

            while (rs.next()) {
                int idVenda = rs.getInt("IdVenda");
                Venda venda = buscarPorId(idVenda, clienteData);
                if (venda != null) {
                    vendas.add(venda);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar vendas.");
            e.printStackTrace();
        }

        return vendas;
    }

    // Atualizar venda (desconto e valor total)
    public void atualizar(int idVenda, Venda venda) {
        String sql = "UPDATE Vendas SET Desconto = ?, Valor_Total = ? WHERE IdVenda = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, venda.getDesconto());
            stmt.setDouble(2, venda.ValorTotal());
            stmt.setInt(3, idVenda);

            stmt.executeUpdate();
            System.out.println("Venda atualizada no banco.");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar venda.");
            e.printStackTrace();
        }
    }

    // Deletar venda (e seus itens em cascata)
    public void deletar(int idVenda) {
        String sqlItens = "DELETE FROM Itens_Venda WHERE IdVenda = ?";
        String sqlVenda = "DELETE FROM Vendas WHERE IdVenda = ?";

        Connection conn = null;

        try {
            conn = DataBaseConnection.getConnection();
            conn.setAutoCommit(false);

            // Deletar itens primeiro
            PreparedStatement stmtItens = conn.prepareStatement(sqlItens);
            stmtItens.setInt(1, idVenda);
            stmtItens.executeUpdate();

            // Depois deletar a venda
            PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda);
            stmtVenda.setInt(1, idVenda);
            stmtVenda.executeUpdate();

            conn.commit();
            System.out.println("Venda deletada do banco.");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.err.println("Erro ao deletar venda.");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Buscar vendas por cliente
    public List<Venda> buscarPorCliente(int idCliente, ClienteData clienteData) {
        List<Venda> vendas = new ArrayList<>();
        String sql = "SELECT IdVenda FROM Vendas WHERE IdCliente = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idVenda = rs.getInt("IdVenda");
                Venda venda = buscarPorId(idVenda, clienteData);
                if (venda != null) {
                    vendas.add(venda);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar vendas por cliente.");
            e.printStackTrace();
        }

        return vendas;
    }
}