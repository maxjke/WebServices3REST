package lt.eif.viko.mjakovcenko.bankrest.exception;

public class LoanNotFoundException extends RuntimeException{
    public LoanNotFoundException(Long id) {
        super("Could not find loan " + id);
    }
}

