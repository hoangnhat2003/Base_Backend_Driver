package backend.drivor.base.service.impl;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.repository.AccountRepository;
import backend.drivor.base.domain.request.ChangePasswordRequest;
import backend.drivor.base.domain.components.RedisCache;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import backend.drivor.base.service.ServiceBase;
import backend.drivor.base.service.inf.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl extends ServiceBase implements AccountService {

    /**
     * Rate limit change password.
     * A user only allow type wrong password less than 5 times in 1 hour when change password.
     * @param account
     * @param request
     * @return
     */
    @Override
    public void changePassword(Account account, ChangePasswordRequest request) {

        String key = account.getId() + ":wrong_pw";
        Long currentCount = null;

        boolean isSamePassword = Objects.equals(request.getCurrent_password().trim().toLowerCase(), request.getNew_password().trim().toLowerCase());

        if(redisCache.hasKey(key)) {
            currentCount = Long.parseLong(redisCache.get(key).toString());
        }

        if(currentCount != null && currentCount > 5) {
            throw ServiceExceptionUtils.permissionChangePasswordDenied();
        }

        if(isSamePassword)  {
            throw ServiceExceptionUtils.wrongPassword();
        }
        boolean isMatchesPassword = passwordEncoder.matches(request.getCurrent_password(), account.getPassword());

        if(!isMatchesPassword && !isSamePassword) {
            redisCache.increase(key);
            if(currentCount == null) {
                redisCache.expire(key, (int) TimeUnit.HOURS.toSeconds(1));
            }
            throw ServiceExceptionUtils.wrongPassword();
        }

        if(redisCache.hasKey(key)) {
            redisCache.delete(key);
        }

        String encodedPassword = passwordEncoder.encode(request.getNew_password());
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    @Override
    public Account findAccountByUsername(String username) {

        Account account = null;

        boolean existById = accountRepository.existsByUsername(username);

        if(existById)
            account = (Account) accountRepository.findByUsername(username).get();

        if(account == null)
            throw ServiceExceptionUtils.accountNotFound();

        return account;
    }
}
