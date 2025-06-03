import java.util.*;

public class MainService {
    private Map<String, Client> clients = new HashMap<>();
    private TreeSet<BankAccount> accounts = new TreeSet<>();
    private ArrayList<Card> cards = new ArrayList<>();

    public void addClient(Client client) {
        clients.put(client.getCnp(), client);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addAccountToClient(String idClient, BankAccount cont) {
        Client client = clients.get(idClient);
        if (client != null) {
            client.adaugaCont(cont);
            accounts.add(cont);
        }
    }

    public void showClients() {
        for (Client c : clients.values()) {
            System.out.println(c);
        }
    }

    public void showAccounts() {
        for (BankAccount c : accounts) {
            System.out.println(c);
        }
    }

    public Client findClientByCNP(String cnp) {
        return clients.get(cnp);
    }

    public BankAccount findAccountByIBAN(String iban) {
        for (BankAccount account : accounts) {
            if (account.getIban().equals(iban)) {
                return account;
            }
        }
        return null;
    }
}
