package lt.eif.viko.mjakovcenko.bankrest.repository;

import lt.eif.viko.mjakovcenko.bankrest.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbLoader {
    private ClientRepository clientRepository;

    /**
     * Automatically injects the {@link ClientRepository} dependency and initializes
     * the client data by calling the {@link #loadClient()} method.
     *
     * @param clientRepository the repository used for accessing client data
     */
    @Autowired
    public void DataLoader(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        loadClient();
    }

    /**
     * Creates objects filled with data and saves it with JpaRepository help.
     */
    public void loadClient() {
        Client client1 = new Client("Name1","LastName1");

        Account account1 = new Account("Username1","Password1");
        CreditCard creditCard = new CreditCard(10000.00,"2026-03-13", "123","1234 1234 1234 1234");
        CreditCard creditCard2 = new CreditCard(1000000.00,"2028-03-15", "456","9876 5432 0000 0000");
        CreditCard creditCard3 = new CreditCard(25000.00,"2030-03-15", "001","0000 0000 0000 0000");

        Loan loan1 = new Loan(100.00,10.00,"2024-03-12","2025-01-12",3);
        Loan loan2 = new Loan(10000.00,100.00,"2024-03-15","2030-03-15",2);
        Loan loan3 = new Loan(1000.00,20.00,"2024-03-10","2025-03-10",1);

        BankAccount bankAccount1 = new BankAccount(1000.25, "euro");
        BankAccount bankAccount2 = new BankAccount(10.00, "dollar");
        BankAccount bankAccount3 = new BankAccount(500.00, "pound");

        client1.getBankAccountList().add(bankAccount1);
        client1.getBankAccountList().add(bankAccount2);
        client1.getBankAccountList().add(bankAccount3);
        client1.getLoanList().add(loan1);
        client1.getLoanList().add(loan2);
        client1.getLoanList().add(loan3);
        client1.getCreditCardList().add(creditCard);
        client1.getCreditCardList().add(creditCard2);
        client1.getCreditCardList().add(creditCard3);

        client1.setAccount(account1);


        clientRepository.save(client1);
    }
}
