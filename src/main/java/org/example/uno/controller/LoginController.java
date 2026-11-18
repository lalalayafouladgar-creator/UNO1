
package org.example.uno.controller;

import org.example.uno.model.Account;

public class LoginController {
    private AccountManager accountManager;

    public LoginController(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public boolean register(String username, String password, String displayName) {
        return accountManager.register(username, password, displayName);
    }

    public boolean login(String username, String password) {
        return accountManager.login(username, password);
    }

    public void logout() {
        accountManager.logout();
    }

    public Account getCurrentAccount() {
        return accountManager.getCurrentAccount();
    }
}
