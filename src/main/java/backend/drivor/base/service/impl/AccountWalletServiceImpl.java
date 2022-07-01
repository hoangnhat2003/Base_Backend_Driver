package backend.drivor.base.service.impl;

import backend.drivor.base.domain.components.RedisCache;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.AccountWallet;
import backend.drivor.base.domain.repository.AccountWalletRepository;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.service.inf.AccountWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountWalletServiceImpl implements AccountWalletService {

    private static final String TAG = AccountWalletServiceImpl.class.getName();

    @Autowired
    private AccountWalletRepository accountWalletRepository;

    @Override
    public AccountWallet getOrCreateAccountWallet(Account account, String walletType) {

         AccountWallet accountWallet = accountWalletRepository.findByAccountAndWalletType(account, walletType);

         if(accountWallet == null) {
             accountWallet = createAccountWallet(account, walletType);
         }
        return null;
    }

    private AccountWallet createAccountWallet(Account account, String walletType) {

        AccountWallet accountWallet = null;
        try {
            accountWallet.setAccount(account);
            accountWallet.setBalance(0);
            accountWallet.setWalletType(walletType);
            accountWallet = accountWalletRepository.save(accountWallet);

            if(accountWallet == null) {
                LoggerUtil.e(TAG,"Error while saving new account wallet into database" );
                throw new Exception("Error while saving new account wallet into database");
            }
            return accountWallet;
        }catch (Exception e) {
              LoggerUtil.e(TAG,e.getMessage());
              throw new RuntimeException(e);
        }
    }
}
