package lt.eif.viko.mjakovcenko.bankrest.model;

import jakarta.persistence.*;
/**
 * CreditCard class represents clients CreditCard data. It loads data about this class from database.
 *
 */
@Entity
@Table(name="creditcard")
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    /**
     * The credit limit of the card.
     */
    private double cardLimit;

    /**
     * The expiration date of the card in a String format.
     */
    private String expireDate;

    /**
     * The CVC (Card Verification Code) of the card.
     */
    private String CVC;

    /**
     * The card number.
     */
    private String cardNumber;


    //Constructor

    /**
     * Constructor with parameters
     * @param cardLimit The credit limit of the card.
     * @param expireDate The expiration date of the card.
     * @param CVC The CVC code of the card.
     * @param cardNumber The number of the card.
     */
    public CreditCard(double cardLimit, String expireDate, String CVC, String cardNumber) {
        this.cardLimit = cardLimit;
        this.expireDate = expireDate;
        this.CVC = CVC;
        this.cardNumber = cardNumber;
    }

    /**
     * Non-arg constructor
     */

    public CreditCard() {
    }
    /**
     * toString method override to print information about credit card.
     * @return data of card limit, card expire date, card cvc code, card number.
     */
    @Override
    public String toString() {
        return String.format("\t\t\t\t\t\t\tCredit card:\n " +
                        "\t\t\t\t\t\t\t\t\tCard limit: %s\n" +
                        "\t\t\t\t\t\t\t\t\tCard expire date: %s\n" +
                        "\t\t\t\t\t\t\t\t\tCard CVC code: %s\n" +
                        "\t\t\t\t\t\t\t\t\tCard number: %s\n",this.cardLimit,this.expireDate,this.CVC,this.cardNumber);
    }

    //Getters and setters

    public double getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getCVC() {
        return CVC;
    }

    public void setCVC(String CVC) {
        this.CVC = CVC;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
