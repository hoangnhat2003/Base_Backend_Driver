package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    <T> Optional<T> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
