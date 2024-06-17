package lt.eif.viko.mjakovcenko.bankrest.exception;

public class BankAccountNotFoundException extends RuntimeException{
    public BankAccountNotFoundException(Long id) {
        super("Could not find bank account " + id);
    }
}
