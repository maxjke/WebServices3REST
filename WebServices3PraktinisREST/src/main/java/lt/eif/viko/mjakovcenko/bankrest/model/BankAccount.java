package lt.eif.viko.mjakovcenko.bankrest.model;

import jakarta.persistence.*;


/**
 * Bank account class represents data of a bank account of a client. It loads data about this class from database.
 * @see Account
 * @see Client
 * @see Loan
 * @see CreditCard
 */
@Entity
@Table(name="bankaccount")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    /**
     * The balance of the bank account.
     */
    private double balance;

    /**
     * The currency of the bank account balance.
     */
    private String currency;


    //Constructor

    /**
     * Constructor with parameters
     * @param balance The initial balance of the bank account.
     * @param currency The currency of the bank account balance.
     */
    public BankAccount(double balance, String currency) {
        this.balance = balance;
        this.currency = currency;
    }

    /**
     * Non-arg constructor
     */
    public BankAccount() {
    }

    /**
     * toString method override to print information about bank account.
     * @return data of balance and currency.
     */
    @Override
    public String toString() {
        return String.format("\t\tBank Account: \n" +
                "\t\t\t Balance: %s\n" +
                "\t\t\t Currency: %s\n", this.balance,this.currency);
    }

    //Getters and Setters
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
