import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardService {

    public void createTable() {
        try (Connection con = DbConnection.getInstance();
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS cards (
                    cardNumber VARCHAR(30) PRIMARY KEY,
                    ownerCnp VARCHAR(20),
                    expirationDate VARCHAR(10),
                    cvv INT,
                    balance DOUBLE,
                    suspended BOOLEAN,
                    FOREIGN KEY (ownerCnp) REFERENCES clients(cnp)
                )
            """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(Card card) {
        String sql = "INSERT INTO cards (cardNumber, ownerCnp, expirationDate, cvv, balance, suspended) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, card.getCardNumber());
            ps.setString(2, card.getOwner().getCnp());
            ps.setString(3, card.getExpirationDate());
            ps.setInt(4, card.getCvv());
            ps.setDouble(5, card.getBalance());
            ps.setBoolean(6, !card.checkActive());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Card> getAll() {
        // You need a concrete Card subclass to instantiate here
        return new ArrayList<>();
    }

    public void updateBalance(String cardNumber, double newBalance) {
        String sql = "UPDATE cards SET balance=? WHERE cardNumber=?";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, newBalance);
            ps.setString(2, cardNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String cardNumber) {
        String sql = "DELETE FROM cards WHERE cardNumber=?";
        try (Connection con = DbConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cardNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}