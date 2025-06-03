import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BankAccountService {

    public void createTable() {
        try (Connection con = DbConnection.getInstance();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS bank_accounts (
                    iban VARCHAR(34) PRIMARY KEY,
                    balance DOUBLE,
                    ownerCnp VARCHAR(20),
                    FOREIGN KEY (ownerCnp) REFERENCES clients(cnp)
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(BankAccount account) {
        String sql = "INSERT INTO bank_accounts (iban, balance, ownerCnp) VALUES (?, ?, ?)";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, account.getIban());
            ps.setDouble(2, account.getBalance());
            ps.setString(3, account.getOwner().getCnp());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BankAccount> getAll() {
        // You need a concrete BankAccount subclass to instantiate here
        return new ArrayList<>();
    }

    public void updateBalance(String iban, double newBalance) {
        String sql = "UPDATE bank_accounts SET balance=? WHERE iban=?";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setString(2, iban);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String iban) {
        String sql = "DELETE FROM bank_accounts WHERE iban=?";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, iban);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}