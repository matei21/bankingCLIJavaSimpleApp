public class Transaction {
    static int idx = 0;
    private int id;
    private String fromIban;
    private String toIban;
    private double amount;
    private String timestamp; // or use java.sql.Timestamp

    public Transaction(String fromIban, String toIban, double amount, String timestamp) {
        this.id = idx++;
        this.fromIban = fromIban;
        this.toIban = toIban;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public String getFromIban() { return fromIban; }
    public String getToIban() { return toIban; }
    public double getAmount() { return amount; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return id + ": " + fromIban + " -> " + toIban + " | " + amount + " | " + timestamp;
    }
}