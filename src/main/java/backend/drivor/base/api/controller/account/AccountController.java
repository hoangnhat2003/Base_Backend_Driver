package backend.drivor.base.api.controller.account;

import backend.drivor.base.api.controller.BaseController;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.document.RefreshToken;
import backend.drivor.base.domain.request.ChangePasswordRequest;
import backend.drivor.base.domain.request.ChatAccountRequest;
import backend.drivor.base.domain.request.RefreshTokenRequest;
import backend.drivor.base.domain.response.ApiResponse;
import backend.drivor.base.domain.response.ChatAccountResponse;
import backend.drivor.base.domain.response.TokenRefreshResponse;
import backend.drivor.base.domain.utils.LoggerUtil;
import backend.drivor.base.domain.utils.ServiceExceptionUtils;
import backend.drivor.base.service.inf.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/account")
@RestController
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request) {

        Account account = getLoggedAccount();
        accountService.changePassword(account, request);

        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name() ,"Change password successfully!", null));
    }

    @PostMapping("/chat_account")
    public ResponseEntity<?> createChatAccount(@Valid @RequestBody ChatAccountRequest request) {

        Account account = getLoggedAccount();

        ChatAccountResponse response = accountService.createChatAccount(account, request);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name() ,"Create chat account successfully!", response));
    }

}
