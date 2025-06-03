import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CreditCard extends Card {
    protected double creditLimit;
    protected double minimumPayment;
    private double debt;
    protected double interestRate;
    private static final TransactionService transactionService = new TransactionService();

    LocalDate lastPaymentDate;

    public CreditCard(String cardNumber, Client owner, String expirationDate, int cvv, double balance, double creditLimit, double minimumPayment, double debt, double interestRate) {
        super(cardNumber, owner, expirationDate, cvv, balance);
        this.creditLimit = creditLimit;
        this.minimumPayment = minimumPayment;
        this.debt = debt;
        this.interestRate = interestRate;
        this.lastPaymentDate = LocalDate.now();
    }

    public CreditCard(double creditLimit, double minimumPayment, double debt) {
        super();
        this.creditLimit = creditLimit;
        this.debt = debt;
        this.minimumPayment = minimumPayment;
        this.interestRate = interestRate;
        this.lastPaymentDate = LocalDate.now();
    }

    public CreditCard(){
        super();
        this.creditLimit = 0.0;
        this.minimumPayment = 0.0;
        this.debt = 0.0;
        this.interestRate = 0.03;
        this.lastPaymentDate = LocalDate.now();
    }

    double getCreditLimit() {
        return creditLimit;
    }

    double getMinimumPayment() {
        return minimumPayment;
    }

    double getDebt() {
        return debt;
    }

    double getInterestRate() {
        return interestRate;
    }

    LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    @Override
    public void pay(double sum) {
        if(sum < minimumPayment) {
            System.out.println("Payment of " + sum + " is below the minimum payment of " + minimumPayment + ". Payment not successful.");
            return;
        }

        if(debt > 0 && ChronoUnit.DAYS.between(lastPaymentDate, LocalDate.now()) > 90) {
            debt += debt * interestRate;
        }

        if(balance >= sum){
            balance -= sum;
            System.out.println("Payment of " + sum + " has been successful.");
            return;
        } else
        if (sum <= (creditLimit + balance - debt)) {
            balance = 0;
            debt += sum - balance;
            if(debt > creditLimit) {
                System.out.println("Warning: Credit limit has now been exceeded.");
            }
            System.out.println("Payment of " + sum + " has been successful.");
            transactionService.insert(cardNumber, cardNumber, -1 * sum); }
        else{
            System.out.println("Payment of " + sum + " was not successful. Credit limit exceeded.");
        }
    }

    @Override
    public void recharge(double sum) {
        if(sum <= debt){
            debt -= sum;
            System.out.println("Debt of " + sum + " has been paid.");
            lastPaymentDate = LocalDate.now();
            transactionService.insert(cardNumber, cardNumber, sum);
        }
        else{
            balance = sum - debt;
            debt = 0;
            System.out.println("Debt has been paid fully. Now balance is " + balance + ".");
            lastPaymentDate = LocalDate.now();
        }
    }

    @Override
    public String toString() {
        return super.toString() + " Credit limit: " + creditLimit + ", debt: " + debt + "]";
    }
}
