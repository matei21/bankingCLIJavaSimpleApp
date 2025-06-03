public class SimpleAccount extends BankAccount {
    protected double administrationFee;
    private static final TransactionService transactionService = new TransactionService();

    public SimpleAccount(String iban, double initialBalance, Client client, double administrationFee) {
        super(iban, initialBalance, client);
        this.administrationFee = administrationFee;
    }

    public SimpleAccount(String iban, double initialBalance, double administrationFee) {
        super(iban, initialBalance);
        this.administrationFee = administrationFee;
    }

    public SimpleAccount(double administrationFee) {
        super();
        this.administrationFee = administrationFee;
    }

    public SimpleAccount(){
        super();
        this.administrationFee = 10.0;
    }

    @Override
    public void withdraw(double sum) {
        if(!checkOwnerCNP()){
            System.out.println("Withdrawal failed due to CNP verification.");
            return;
        }

        if (sum <= balance) {
            balance -= sum;
            System.out.println("Withdrawal succesful: " + sum + " RON");
            transactionService.insert(super.getIban(), super.getIban(), sum); // Assuming null for 'toIban' in withdrawal

        }
        else {
            System.out.println("Insufficient funds for withdrawal: " + sum + " RON");
        }
    }

    @Override
    public void transfer(double sum, BankAccount otherAccount)  {
        if(!checkOwnerCNP()){
            System.out.println("Transfer failed due to CNP verification.");
            return;
        }

        if (sum <= balance) {
            balance -= sum;
            System.out.println("Transfer succesful: " + sum + " RON");
            otherAccount.deposit(sum);
            transactionService.insert(super.getIban(), otherAccount.getIban(), sum);
        } else {
            System.out.println("Insufficient funds for transfer: " + sum + " RON");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " [Simple account, Administration fee: " + administrationFee + "]";
    }
}
