package backend.drivor.base.service.impl;

import backend.drivor.base.domain.components.RedisCache;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.AccountWallet;
import backend.drivor.base.domain.repository.AccountWalletRepository;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.service.ServiceBase;
import backend.drivor.base.service.inf.AccountWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountWalletServiceImpl extends ServiceBase implements AccountWalletService {

    private static final String TAG = AccountWalletServiceImpl.class.getName();


    @Override
    public AccountWallet getOrCreateAccountWallet(Account account, String walletType) {

         AccountWallet accountWallet = accountWalletRepository.findByAccountAndWalletType(account, walletType);

         if(accountWallet == null) {
             accountWallet = createAccountWallet(account, walletType);
             return accountWallet;
         }
        return new AccountWallet();
    }

    @Override
    public void lockBalance(AccountWallet accountWallet, long amount) {
        long balance = accountWallet.getBalance();
        try {
            long updatedBalance = balance - amount;
            accountWallet.setBalance(updatedBalance);
            accountWalletRepository.save(accountWallet);
        }catch (Exception e) {
            LoggerUtil.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void unlockBalance(AccountWallet accountWallet, long amount) {
        long balance = accountWallet.getBalance();
        try {
            long updatedBalance = balance + amount;
            accountWallet.setBalance(updatedBalance);
            accountWalletRepository.save(accountWallet);
        }catch (Exception e) {
            LoggerUtil.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private AccountWallet createAccountWallet(Account account, String walletType) {

        try {
            AccountWallet accountWallet = new AccountWallet();
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
