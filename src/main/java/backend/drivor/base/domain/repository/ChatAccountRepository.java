package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.ChatAccount;
import org.springframework.data.repository.CrudRepository;

public interface ChatAccountRepository extends CrudRepository<ChatAccount, Long> {
    ChatAccount findByUsername(String username);

    ChatAccount findByAccount(Account account);
}
