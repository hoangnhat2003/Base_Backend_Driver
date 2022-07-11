package backend.drivor.base.service.impl;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.ChatAccount;
import backend.drivor.base.domain.repository.AccountRepository;
import backend.drivor.base.domain.repository.ChatAccountRepository;
import backend.drivor.base.domain.request.ChangePasswordRequest;
import backend.drivor.base.domain.components.RedisCache;
import backend.drivor.base.domain.request.ChatAccountRequest;
import backend.drivor.base.domain.response.ChatAccountResponse;
import backend.drivor.base.domain.utils.CastTypeUtils;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import backend.drivor.base.service.ServiceBase;
import backend.drivor.base.service.inf.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl extends ServiceBase implements AccountService {

    private static final String TAG = AccountServiceImpl.class.getSimpleName();

    @Autowired
    private ChatAccountRepository chatAccountRepository;

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
            currentCount = CastTypeUtils.toLong(redisCache.get(key).toString());
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

    @Override
    public ChatAccountResponse createChatAccount(Account account, ChatAccountRequest request) {

        ChatAccount chatAccount = chatAccountRepository.findByUsername(request.getUsername());

        if(chatAccount != null) {
            throw ServiceExceptionUtils.valueExists("chat_username");
        }

        try {
            chatAccount = new ChatAccount();
            chatAccount.setAccount(account);
            chatAccount.setUsername(request.getUsername());
            chatAccount.setPassword(request.getPassword());
            chatAccount.setCreatedDate(new Date());

            chatAccountRepository.save(chatAccount);

        }catch (NullPointerException e) {
             LoggerUtil.e(TAG, e.getMessage());
             throw new RuntimeException(e);
        }catch (Exception e) {
            LoggerUtil.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }

        ChatAccountResponse response = ChatAccountResponse.builder()
                .id(chatAccount.getId())
                .accountId(account.getId())
                .chat_password(chatAccount.getUsername())
                .chat_username(chatAccount.getUsername())
                .build();

        return response;
    }

    @Override
    public ChatAccountResponse getChatAccountInfo(Optional<String> username) {

        if(!username.isPresent()) {
            throw ServiceExceptionUtils.missingParam("username");
        }

        ChatAccount chatAccount = chatAccountRepository.findByUsername(username.get());

        if(chatAccount == null) {
            throw ServiceExceptionUtils.accountNotFound();
        }

        ChatAccountResponse response = ChatAccountResponse.builder()
                .id(chatAccount.getId())
                .accountId(chatAccount.getAccount().getId())
                .chat_password(chatAccount.getUsername())
                .chat_username(chatAccount.getUsername())
                .build();

        return response;
    }

    @Override
    public ChatAccount getChatAccountByAccount(Account account) {

        Optional<ChatAccount> chatAccount = Optional.ofNullable(chatAccountRepository.findByAccount(account));

        if(!chatAccount.isPresent()) {
            throw ServiceExceptionUtils.accountNotFound();
        }
        return chatAccount.get();
    }

    @Override
    public Account findAccountById(Long id) {

        Optional<Account> account = accountRepository.findById(id);

        if(!account.isPresent()) {
            throw ServiceExceptionUtils.accountNotFound();
        }
        return account.get();
    }
}
