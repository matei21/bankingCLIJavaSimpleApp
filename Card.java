public abstract class Card {
    public String cardNumber;
    protected Client owner;
    protected String expirationDate;
    protected int cvv;
    protected double balance;
    private boolean suspended;

    public Card(String cardNumber, Client owner, String expirationDate, int cvv, double balance) {
        this.cardNumber = cardNumber;
        this.owner = owner;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.balance = balance;
        this.suspended = false;
    }

    public Card(){
        this.cardNumber = "";
        this.owner = new Client();
        this.expirationDate = "";
        this.cvv = 0;
        this.balance = 0.0;
        this.suspended = false;
    }

    public abstract void pay(double sum);
    public abstract void recharge(double sum);
    public void activate(){
        if(suspended) {
            suspended = false;
            System.out.println("Card has been re-activated.");
        }else{
            System.out.println("Card is already active.");
        }
    }

    public void suspend(){
        if(!suspended) {
            suspended = true;
            System.out.println("Card has been suspended.");
        }else{
            System.out.println("Card is already suspended.");
        }
    }

    public boolean checkActive(){
        return !suspended;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Client getOwner() {
        return owner;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public int getCvv() {
        return cvv;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Card: " + cardNumber + ", owner: " + owner;
    }
}
