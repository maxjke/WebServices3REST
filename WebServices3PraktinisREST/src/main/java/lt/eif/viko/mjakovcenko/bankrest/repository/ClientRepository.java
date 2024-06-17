package lt.eif.viko.mjakovcenko.bankrest.repository;

import lt.eif.viko.mjakovcenko.bankrest.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
