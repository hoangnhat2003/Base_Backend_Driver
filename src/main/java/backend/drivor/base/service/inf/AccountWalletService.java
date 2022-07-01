package backend.drivor.base.service.inf;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.AccountWallet;

public interface AccountWalletService {

     AccountWallet getOrCreateAccountWallet(Account account, String walletType);

    void lockBalance(AccountWallet accountWallet, long amount);

    void unlockBalance(AccountWallet accountWallet, long amount);
}
