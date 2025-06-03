import java.util.Scanner;

public abstract class BankAccount implements Comparable<BankAccount> {
    public String IBAN;
    protected double balance;
    protected Client owner;

    public BankAccount(String IBAN, double balance, Client owner) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.owner = owner;
    }

    public BankAccount(String IBAN, double balance) {
        this.IBAN = IBAN;
        this.balance = balance;
        this.owner = new Client();
    }

    public BankAccount(){
        this.IBAN = "";
        this.balance = 0.0;
        this.owner = new Client();
    }

    public Client getOwner(){
        return owner;
    }

    public String getIban() {
        return IBAN;
    }

    public double getBalance() {
        return balance;
    }

    public boolean checkOwnerCNP(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter CNP for verification: ");
        String cnp = scanner.nextLine();
        if(cnp.equals(owner.getCnp())){
            System.out.println("CNP verified successfully.");
            return true;
        } else {
            System.out.println("CNP verification failed.");
            return false;
        }
    }

    public void deposit(double sum) {
        balance += sum;
    }

    public abstract void withdraw(double sum);

    public abstract void transfer(double sum, BankAccount otherAccount);

    @Override
    public String toString() {
        return "For the account, IBAN: " + IBAN + ", Balance: " + balance + " RON";
    }

    @Override
    public int compareTo(BankAccount otherAccount) {
        return Double.compare(this.balance, otherAccount.balance);
    }
}
