package lt.eif.viko.mjakovcenko.bankrest.repository;

import lt.eif.viko.mjakovcenko.bankrest.model.Account;
import lt.eif.viko.mjakovcenko.bankrest.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
