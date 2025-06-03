import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    public void createTable() {
        try (Connection con = DbConnection.getInstance();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS transactions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    fromIban VARCHAR(34),
                    toIban VARCHAR(34),
                    amount DOUBLE,
                    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    FOREIGN KEY (fromIban) REFERENCES bank_accounts(iban),
                    FOREIGN KEY (toIban) REFERENCES bank_accounts(iban)
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String fromIban, String toIban, double amount) {
        String sql = "INSERT INTO transactions (fromIban, toIban, amount) VALUES (?, ?, ?)";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fromIban);
            ps.setString(2, toIban);
            ps.setDouble(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAll() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection con = DbConnection.getInstance();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Transaction(
                        rs.getString("fromIban"),
                        rs.getString("toIban"),
                        rs.getDouble("amount"),
                        rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void delete(int id) {
        String sql = "DELETE FROM transactions WHERE id=?";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}