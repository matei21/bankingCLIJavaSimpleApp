import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        MainService bank = new MainService();
        AuditService audit = AuditService.getInstance();

        Scanner scanner = new Scanner(System.in);
        Connection db = DbConnection.getInstance();
        BankAccountService bankAccountService = new BankAccountService();
        ClientService clientService = new ClientService();
        CardService cardService = new CardService();
        clientService.createTable();
        bankAccountService.createTable();
        cardService.createTable();


        System.out.println("1. Defineste un client");
        System.out.println("2. Defineste un cont");
        System.out.println("3. Deschide un cont curent");
        System.out.println("4. Deschide un cont de economii");
        System.out.println("5. Depune bani in contul curent");
        System.out.println("6. Depune bani in contul de economii");
        System.out.println("7. Retrage bani din contul curent");
        System.out.println("8. Retrage bani din contul de economii");
        System.out.println("9. Afiseaza detalii despre client si conturi");
        System.out.println("10. Creaza un card de debit");
        System.out.println("11. Creaza un card de credit");
        System.out.println("12. Exit");

        int optiune = 0;
        while(optiune != 12) {
            System.out.print("Alege o optiune: ");
            optiune = scanner.nextInt();
            scanner.nextLine();

            switch(optiune) {
                case 1: // Add client
                    System.out.print("CNP: ");
                    String cnp = scanner.nextLine();
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    Client client = new Client(cnp, name);
                    bank.addClient(client);
                    audit.logAction("add_client");
                    clientService.insert(client);
                    System.out.println("Client added.");
                    break;
                case 2: // Add account to client
                    System.out.print("CNP client: ");
                    String cnpAcc = scanner.nextLine();
                    System.out.print("IBAN: ");
                    String iban = scanner.nextLine();
                    System.out.print("Balance: ");
                    double balance = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.println("Administration fee (0 for simple account): ");
                    double adminFee = scanner.nextDouble();
                    BankAccount acc = new SimpleAccount(iban, balance, bank.findClientByCNP(cnpAcc), adminFee);
                    bank.addAccountToClient(cnpAcc, acc);
                    bankAccountService.createTable();
                    bankAccountService.insert(acc);
                    audit.logAction("add_account_to_client");
                    System.out.println("Account added to client.");
                    break;
                case 3: // Open current account
                    System.out.print("CNP client: ");
                    String cnpCur = scanner.nextLine();
                    System.out.print("IBAN: ");
                    String ibanCur = scanner.nextLine();
                    System.out.print("Balance: ");
                    double balCur = scanner.nextDouble();
                    scanner.nextLine();
                    double administrationFee = scanner.nextDouble();
                    SimpleAccount curAcc = new SimpleAccount(ibanCur, balCur, bank.findClientByCNP(cnpCur), administrationFee);
                    bank.addAccountToClient(cnpCur, curAcc);
                    audit.logAction("open_current_account");
                    System.out.println("Current account opened.");
                    break;
                case 4: // Open savings account
                    System.out.print("CNP client: ");
                    String cnpSav = scanner.nextLine();
                    System.out.print("IBAN: ");
                    String ibanSav = scanner.nextLine();
                    System.out.print("Balance: ");
                    double balSav = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Interest rate: ");
                    double rate = scanner.nextDouble();
                    scanner.nextLine();
                    SavingsAccount savAcc = new SavingsAccount(ibanSav, balSav, bank.findClientByCNP(cnpSav), rate);
                    bank.addAccountToClient(cnpSav, savAcc);
                    audit.logAction("open_savings_account");
                    bankAccountService.insert(savAcc);
                    System.out.println("Savings account opened.");
                    break;
                case 5: // Deposit to current account
                    System.out.print("IBAN: ");
                    String ibanDep = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amtDep = scanner.nextDouble();
                    scanner.nextLine();
                    SimpleAccount currentAccount = (SimpleAccount) bank.findAccountByIBAN(ibanDep);
                    currentAccount.deposit(amtDep);
                    audit.logAction("deposit_current_account");
                    bankAccountService.updateBalance(ibanDep, currentAccount.getBalance());
                    System.out.println("Deposit successful.");
                    break;
                case 6: // Deposit to savings account
                    System.out.print("IBAN: ");
                    String ibanDepSav = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amtDepSav = scanner.nextDouble();
                    scanner.nextLine();
                    SavingsAccount savingsAccount = (SavingsAccount) bank.findAccountByIBAN(ibanDepSav);
                    savingsAccount.deposit(amtDepSav);
                    audit.logAction("deposit_savings_account");
                    bankAccountService.updateBalance(ibanDepSav, savingsAccount.getBalance());
                    System.out.println("Deposit successful.");
                    break;
                case 7: // Withdraw from current account
                    System.out.print("IBAN: ");
                    String ibanW = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amtW = scanner.nextDouble();
                    scanner.nextLine();
                    SimpleAccount simpleAccount = (SimpleAccount) bank.findAccountByIBAN(ibanW);
                    simpleAccount.deposit(amtW);
                    audit.logAction("withdraw_current_account");
                    bankAccountService.updateBalance(ibanW, simpleAccount.getBalance());
                    System.out.println("Withdrawal successful.");
                    break;
                case 8: // Withdraw from savings account
                    System.out.print("IBAN: ");
                    String ibanWS = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amtWS = scanner.nextDouble();
                    scanner.nextLine();
                    SavingsAccount account = (SavingsAccount) bank.findAccountByIBAN(ibanWS);
                    account.withdraw(amtWS);
                    audit.logAction("withdraw_savings_account");
                    bankAccountService.updateBalance(ibanWS, account.getBalance());
                    System.out.println("Withdrawal successful.");
                    break;
                case 9: // Show client and accounts
                    bank.showClients();
                    bank.showAccounts();
                    audit.logAction("show_clients_and_accounts");
                    clientService.getAll().forEach(System.out::println);
                    break;
                case 10: // Create debit card
                    System.out.print("Card number: ");
                    String cardNum = scanner.nextLine();
                    System.out.print("CNP owner: ");
                    String cnpOwner = scanner.nextLine();
                    System.out.print("Expiration date: ");
                    String exp = scanner.nextLine();
                    System.out.print("CVV: ");
                    int cvv = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Balance: ");
                    double balance2 = scanner.nextDouble();
                    scanner.nextLine();
                    int pin = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Associated account IBAN: ");
                    String ibanAcc = scanner.nextLine();
                    BankAccount associatedAccount = bank.findAccountByIBAN(ibanAcc);
                    DebitCard debitCard = new DebitCard(cardNum, bank.findClientByCNP(cnpOwner), exp, cvv, balance2, pin, associatedAccount);
                    bank.addCard(debitCard);
                    audit.logAction("create_debit_card");
                    cardService.insert(debitCard);
                    System.out.println("Debit card created.");
                    break;
                case 11: // Create credit card
                    System.out.print("Card number: ");
                    String cardNumC = scanner.nextLine();
                    System.out.print("CNP owner: ");
                    String cnpOwnerC = scanner.nextLine();
                    System.out.print("Expiration date: ");
                    String expC = scanner.nextLine();
                    System.out.print("CVV: ");
                    int cvvC = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Balance: ");
                    double balanceC = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Credit limit: ");
                    double limit = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Minimum payment: ");
                    double minPayment = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Debt: ");
                    double debt = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Interest rate: ");
                    double interestRate = scanner.nextDouble();
                    scanner.nextLine();
                    CreditCard creditCard = new CreditCard(cardNumC, bank.findClientByCNP(cnpOwnerC), expC, cvvC, balanceC, limit, minPayment, debt, interestRate);
                    bank.addCard(creditCard);
                    audit.logAction("create_credit_card");
                    cardService.insert(creditCard);
                    System.out.println("Credit card created.");
                    break;
                case 12:
                    System.out.println("Iesi din aplicatie.");
                    audit.logAction("exit");
                    break;
                default:
                    System.out.println("Optiune invalida. Te rog incearca din nou.");
            }
        }
    }
}