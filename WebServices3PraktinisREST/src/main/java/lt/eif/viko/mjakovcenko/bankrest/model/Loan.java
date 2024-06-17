package lt.eif.viko.mjakovcenko.bankrest.model;

import jakarta.persistence.*;

/**
 * Loan class represents data about loan. It loads data about this class from database.
 * @see Client
 * @see CreditCard
 * @see Account
 * @see BankAccount
 *
 */
@Entity
@Table(name="loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long id;

    /**
     * The total amount of the loan.
     */
    private double loanAmount;

    /**
     * The amount that must be paid monthly by the borrower.
     */
    private double monthlyPayment;

    /**
     * The start date of the loan period.
     */
    private String loanStartDate;

    /**
     * The end date of the loan period.
     */
    private String loanEndDate;

    /**
     * The interest rate of the loan, expressed as a percentage.
     */
    private int loanPercent;

    //Constructor

    /**
     * Constructor with parameters
     * @param loanAmount The total amount of the loan.
     * @param monthlyPayment The monthly payment amount.
     * @param loanStartDate The start date of the loan.
     * @param loanEndDate The end date of the loan.
     * @param loanPercent The loan's interest rate as a percentage.
     */
    public Loan(double loanAmount, double monthlyPayment, String loanStartDate, String loanEndDate, int loanPercent) {
        this.loanAmount = loanAmount;
        this.monthlyPayment = monthlyPayment;
        this.loanStartDate = loanStartDate;
        this.loanEndDate = loanEndDate;
        this.loanPercent = loanPercent;
    }

    /**
     * Non-arg constructor
     */
    public Loan() {
    }
    /**
     * toString method override to print information about  Loan.
     * @return data of Loan amount, monthly payment, loan start date, loan end date, loan percent.
     */
    @Override
    public String toString() {
        return String.format("\t\t\t\tLoan: \n" +
                "\t\t\t\t\tLoan amount: %s\n" +
                "\t\t\t\t\tMonthly payment: %s\n" +
                "\t\t\t\t\tLoan start date: %s\n" +
                "\t\t\t\t\tLoan end date: %s\n" +
                "\t\t\t\t\tLoan percent: %s \n", this.loanAmount,this.monthlyPayment,this.loanStartDate,
                this.loanEndDate,this.loanPercent);
    }

    //Getters and Setters
    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public String getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public String getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public int getLoanPercent() {
        return loanPercent;
    }

    public void setLoanPercent(int loanPercent) {
        this.loanPercent = loanPercent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
