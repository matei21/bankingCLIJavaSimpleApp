import java.util.ArrayList;
import java.util.List;

public class Client {
    private String cnp;
    public String name;
    protected ArrayList<BankAccount> accounts;
    static int id = 0;

    public Client(String cnp, String nume, ArrayList<BankAccount> accounts) {
        this.cnp = cnp;
        this.name = nume;
        this.accounts = accounts;
    }

    public Client(String cnp, String name) {
        this.cnp = cnp;
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public Client(){
        this.cnp = String.valueOf(id++);
        this.name = "John Doe" + id;
        this.accounts = new ArrayList<>();
    }

    public String getCnp() {
        return cnp;
    }

    public String getName() {
        return name;
    }

    public void adaugaCont(BankAccount cont) {
        accounts.add(cont);
    }

    public List<BankAccount> getAccounts() {
        return accounts;}

    public long getNetWorth(){
        long total = 0;
        for (BankAccount c : accounts){
            total += c.getBalance();
        }
        return total;
    }

    @Override
    public String toString() {
        return "The client " + name + ", with the CNP " + cnp + ", has " + accounts.size() + " accounts, more exactly: " + accounts;
    }
}
