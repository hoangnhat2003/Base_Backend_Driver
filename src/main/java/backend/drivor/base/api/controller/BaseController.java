package backend.drivor.base.api.controller;

import backend.drivor.base.domain.document.Account;
import backend.drivor.base.service.inf.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class BaseController {

    @Autowired
    protected AccountService accountService;

    protected Account getLoggedAccount() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object o = authentication.getPrincipal();
        String username;
        if (o instanceof UserDetails) ((UserDetails) o).getUsername();
        username = o.toString();

        Account account = accountService.findAccountByUsername(username);

        return  account;
    }
}
