package lt.eif.viko.mjakovcenko.bankrest.model;
import jakarta.persistence.*;

/**
 * Account class represents account data. It loads data about this class from database.
 *
 * @see BankAccount
 * @see Client
 * @see CreditCard
 * @see Loan
 */
@Entity
@Table(name="account")
public class Account {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;
    /**
     * The username of the account.
     */
    private String username;

    /**
     * The password of the account.
     */
    private String password;


    //Constructor

    /**
     * Constructor with parameters.
     * @param username The username for the account.
     * @param password The password for the account.
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     *  None-arg constructor
     */
    public Account() {
    }
    /**
     * toString method override to print information about account.
     * @return data of username and password.
     */
    @Override
    public String toString() {
        return String.format("\t\tUsername: %s\n" +
                "\t\tPassword: %s",
                this.username,this.password);
    }

    //Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
