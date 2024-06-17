package lt.eif.viko.mjakovcenko.bankrest.exception;

public class CreditCardNotFoundException extends RuntimeException{
    public CreditCardNotFoundException(Long id) {
        super("Could not find credit card " + id);
    }
}

