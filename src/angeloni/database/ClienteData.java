package angeloni.database;

import angeloni.clientes.Cliente;
import angeloni.clientes.ClientePessoaFisica;
import angeloni.clientes.ClientePessoaJuridica;
import angeloni.clientes.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteData {
    // Inserir cliente no banco
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO Clientes (IdCliente, NomeCliente, Telefone, Categoria, Tipo, Cpf, Cnpj) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cliente.getId());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getCategoria().name());

            if (cliente instanceof ClientePessoaFisica) {
                ClientePessoaFisica cpf = (ClientePessoaFisica) cliente;
                stmt.setString(5, "FISICA");
                stmt.setString(6, cpf.getCpf());
                stmt.setString(7, null);
            } else if (cliente instanceof ClientePessoaJuridica) {
                ClientePessoaJuridica cpj = (ClientePessoaJuridica) cliente;
                stmt.setString(5, "JURIDICA");
                stmt.setString(6, null);
                stmt.setString(7, cpj.getCnpj());
            }

            stmt.executeUpdate();
            System.out.println("Cliente inserido no banco com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente!");
            e.printStackTrace();
        }
    }

    // Buscar cliente por ID
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM Clientes WHERE IdCliente = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("Tipo");
                Categoria categoria = Categoria.valueOf(rs.getString("Categoria"));

                if ("FISICA".equals(tipo)) {
                    ClientePessoaFisica cliente = new ClientePessoaFisica(
                            rs.getInt("IdCliente"),
                            rs.getString("NomeCliente"),
                            rs.getString("Telefone"),
                            categoria
                    );
                    cliente.setCpf(rs.getString("Cpf"));
                    return cliente;
                } else {
                    ClientePessoaJuridica cliente = new ClientePessoaJuridica(
                            rs.getInt("IdCliente"),
                            rs.getString("NomeCliente"),
                            rs.getString("Telefone"),
                            categoria
                    );
                    cliente.setCnpj(rs.getString("Cnpj"));
                    return cliente;
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente.");
            e.printStackTrace();
        }

        return null;
    }

    // Listar todos os clientes
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Clientes";

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String tipo = rs.getString("Tipo");
                Categoria categoria = Categoria.valueOf(rs.getString("Categoria"));

                if ("FISICA".equals(tipo)) {
                    ClientePessoaFisica cliente = new ClientePessoaFisica(
                            rs.getInt("IdCliente"),
                            rs.getString("NomeCliente"),
                            rs.getString("Telefone"),
                            categoria
                    );
                    cliente.setCpf(rs.getString("Cpf"));
                    clientes.add(cliente);
                } else {
                    ClientePessoaJuridica cliente = new ClientePessoaJuridica(
                            rs.getInt("IdCliente"),
                            rs.getString("NomeCliente"),
                            rs.getString("Telefone"),
                            categoria
                    );
                    cliente.setCnpj(rs.getString("Cnpj"));
                    clientes.add(cliente);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes.");
            e.printStackTrace();
        }

        return clientes;
    }

    // Atualizar cliente
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE Clientes SET NomeCliente = ?, Telefone = ?, Categoria = ?, Cpf = ?, Cnpj = ? WHERE IdCliente = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getCategoria().name());

            if (cliente instanceof ClientePessoaFisica) {
                ClientePessoaFisica cpf = (ClientePessoaFisica) cliente;
                stmt.setString(4, cpf.getCpf());
                stmt.setString(5, null);
            } else if (cliente instanceof ClientePessoaJuridica) {
                ClientePessoaJuridica cpj = (ClientePessoaJuridica) cliente;
                stmt.setString(4, null);
                stmt.setString(5, cpj.getCnpj());
            }

            stmt.setInt(6, cliente.getId());

            stmt.executeUpdate();
            System.out.println("Cliente atualizado no banco.");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente.");
            e.printStackTrace();
        }
    }

    // Deletar cliente
    public void deletar(int id) {
        String sql = "DELETE FROM Clientes WHERE IdCliente = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Cliente deletado do banco.");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente.");
            e.printStackTrace();
        }
    }
}
