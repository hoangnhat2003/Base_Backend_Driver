package backend.drivor.base.domain.response;

import backend.drivor.base.domain.document.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountShortInfoResponse {

    private String username;
    private String name;
    private String email;
    private String account_status;

    public AccountShortInfoResponse(String email, String username, String name, String accountStatus) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.account_status = accountStatus;
    }


    public AccountShortInfoResponse(Account account) {
        this(account.getEmail(),
                account.getUsername(),
                account.getName(),
                account.getAccount_status());
    }

}
