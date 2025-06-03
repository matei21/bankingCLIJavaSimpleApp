import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientService {

    public void createTable() {
        try (Connection con = DbConnection.getInstance();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS clients (
                    cnp VARCHAR(20) PRIMARY KEY,
                    name VARCHAR(100)
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Client client) {
        String sql = "INSERT INTO clients (cnp, name) VALUES (?, ?)";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, client.getCnp());
            ps.setString(2, client.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getAll() {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (Connection con = DbConnection.getInstance();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Client(
                        rs.getString("cnp"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateName(String cnp, String newName) {
        String sql = "UPDATE clients SET name=? WHERE cnp=?";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setString(2, cnp);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String cnp) {
        String sql = "DELETE FROM clients WHERE cnp=?";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cnp);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}