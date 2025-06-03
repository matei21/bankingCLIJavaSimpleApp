import java.util.Scanner;

public class DebitCard extends Card {
    private int pin;
    private BankAccount associatedAccount;
    private static final TransactionService transactionService = new TransactionService();
    public DebitCard(String cardNumber, Client owner, String expirationDate, int cvv, double balance, int pin, BankAccount associatedAccount) {
        super(cardNumber, owner, expirationDate, cvv, balance);
        this.pin = pin;
        this.associatedAccount = associatedAccount;
    }

    public DebitCard(int pin, BankAccount associatedAccount) {
        super();
        this.pin = pin;
        this.associatedAccount = associatedAccount;
    }

    public DebitCard(){
        super();
        this.pin = 1234;
        this.associatedAccount = new SimpleAccount();
    }


    public void recharge(double suma){
        associatedAccount.deposit(suma);
        transactionService.insert(associatedAccount.getIban(), associatedAccount.getIban(), suma);
        System.out.println("Card recharged with " + suma + " RON.");
    }

    public void pay(double suma, BankAccount destinatar){
        if(!checkActive()) {
            System.out.println("Card is suspended. Cannot make payment.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int pinInput = scanner.nextInt();
        if(pinInput != this.pin){
            System.out.println("Incorrect PIN. Payment failed.");
            return;
        }

        if(suma <= associatedAccount.getBalance()){
            associatedAccount.withdraw(suma);
            destinatar.deposit(suma);
            System.out.println("Plata de " + suma + " RON catre " + destinatar.getIban() + " a fost efectuata cu succes.");
            transactionService.insert(associatedAccount.getIban(), destinatar.getIban(), suma);
        } else {
            System.out.println("Fonduri insuficiente pentru plata.");
        }
    }

    BankAccount getAssociatedAccount() {
        return associatedAccount;
    }

    @Override
    public void pay(double suma){
        associatedAccount.withdraw(suma);
    }

    @Override
    public String toString() {
        return super.toString() + " [Debit]";
    }
}
