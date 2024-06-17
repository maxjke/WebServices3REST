package lt.eif.viko.mjakovcenko.bankrest.repository;

import lt.eif.viko.mjakovcenko.bankrest.model.Account;
import lt.eif.viko.mjakovcenko.bankrest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
