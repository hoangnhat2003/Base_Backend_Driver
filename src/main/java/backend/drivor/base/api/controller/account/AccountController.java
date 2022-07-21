package backend.drivor.base.api.controller.account;

import backend.drivor.base.api.controller.BaseController;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.request.ChangePasswordRequest;
import backend.drivor.base.domain.request.ChatAccountRequest;
import backend.drivor.base.domain.response.ApiResponse;
import backend.drivor.base.domain.response.ChatAccountResponse;
import backend.drivor.base.service.inf.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping("/chat_account")
    public ResponseEntity<?> getChatAccount(@RequestParam(name = "username") Optional<String> username) {

        ChatAccountResponse response = accountService.getChatAccountInfo(username);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name() ,null, response));
    }

}
