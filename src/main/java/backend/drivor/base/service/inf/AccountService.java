package backend.drivor.base.service.inf;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.ChatAccount;
import backend.drivor.base.domain.request.ChangePasswordRequest;
import backend.drivor.base.domain.request.ChatAccountRequest;
import backend.drivor.base.domain.response.ApiResponse;
import backend.drivor.base.domain.response.ChatAccountResponse;

import java.util.Optional;

public interface AccountService{

     void changePassword(Account account, ChangePasswordRequest request);

     Account findAccountByUsername(String username);

    ChatAccountResponse createChatAccount(Account account, ChatAccountRequest request);

    ChatAccountResponse getChatAccountInfo(Optional<String> username);

    ChatAccount getChatAccountByAccount(Account account );

    Account findAccountById(Long id);
}
