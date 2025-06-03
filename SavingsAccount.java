public class SavingsAccount extends BankAccount {
    protected double annualInterestRate;
    private double untouchedBalance;
    private static final TransactionService transactionService = new TransactionService();


    public SavingsAccount(String iban, double initialBalance, Client client, double annualInterestRate) {
        super(iban, initialBalance, client);
        this.annualInterestRate = annualInterestRate;
        this.untouchedBalance = initialBalance;
    }

    public SavingsAccount(String iban, double initialBalance, double annualInterestRate) {
        super(iban, initialBalance);
        this.annualInterestRate = annualInterestRate;
        this.untouchedBalance = initialBalance;
    }

    public SavingsAccount(){
        super();
        this.annualInterestRate = 0.05;
        this.untouchedBalance = (int) super.getBalance();
    }

    public void addInterestRate() {
        balance += untouchedBalance * annualInterestRate;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public void transfer(double amount, BankAccount otherAccount) {
        if (!checkOwnerCNP()) {
            System.out.println("Transfer failed due to CNP verification.");
            return;
        }

        if (amount <= balance) {
            balance -= amount;
            untouchedBalance -= amount;
            otherAccount.deposit(amount);
            System.out.println("Transfer successful: " + amount + " RON");
            transactionService.insert(this.getIban(), otherAccount.getIban(), amount);
        } else {
            System.out.println("Insufficient funds for transfer: " + amount + " RON");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (!checkOwnerCNP()) {
            System.out.println("Withdrawal failed due to CNP verification.");
            return;
        }

        if (amount <= balance) {
            balance -= amount;
            untouchedBalance -= amount;
            System.out.println("Withdrawal successful: " + amount + " RON");
            transactionService.insert(super.getIban(), super.getIban(), -1 * amount);
        } else {
            System.out.println("Insufficient funds for withdrawal: " + amount + " RON");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Economii, Dobandă: " + annualInterestRate * 100 + "%";
    }
}
