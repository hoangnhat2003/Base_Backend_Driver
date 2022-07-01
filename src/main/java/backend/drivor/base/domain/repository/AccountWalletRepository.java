package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountWalletRepository extends CrudRepository<Account, Long> {
}
