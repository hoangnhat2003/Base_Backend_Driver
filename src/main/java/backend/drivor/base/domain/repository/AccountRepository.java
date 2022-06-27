package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
