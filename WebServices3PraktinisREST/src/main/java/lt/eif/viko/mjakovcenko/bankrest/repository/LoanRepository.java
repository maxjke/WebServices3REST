package lt.eif.viko.mjakovcenko.bankrest.repository;

import lt.eif.viko.mjakovcenko.bankrest.model.Account;
import lt.eif.viko.mjakovcenko.bankrest.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
