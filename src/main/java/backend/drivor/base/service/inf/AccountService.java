package backend.drivor.base.service.inf;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.request.ChangePasswordRequest;
import backend.drivor.base.domain.response.ApiResponse;

public interface AccountService {

     void changePassword(Account account, ChangePasswordRequest request);

     Account findAccountByUsername(String username);

}
