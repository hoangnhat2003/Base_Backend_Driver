package backend.drivor.base.domain.repository;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.AccountWallet;
import org.springframework.data.repository.CrudRepository;

public interface AccountWalletRepository extends CrudRepository<AccountWallet, Long> {
    AccountWallet findByAccountAndWalletType(Account account, String debtWallet);
}
