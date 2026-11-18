
package org.example.uno.controller;

import org.example.uno.model.Account;
import org.example.uno.structures.DynamicArray;

public class AccountManager {
    private DynamicArray<Account> accounts;
    private Account currentAccount;

    public AccountManager() {
        accounts = new DynamicArray<>();
        currentAccount = null;
    }

    public boolean register(String username, String password, String displayName) {
        if (username.length() == 0 || username.contains(" ")) {
            return false;
        }
        if (password.length() < 2) {
            return false;
        }
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username)) {
                return false;
            }
        }
        Account account = new Account(username, password, displayName);
        accounts.add(account);
        return true;
    }

    public boolean login(String username, String password) {
        if (currentAccount != null) {
            return false;
        }
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                currentAccount = account;
                return true;
            }
        }
        return false;
    }

    public void logout() {
        currentAccount = null;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public DynamicArray<Account> getAccounts() {
        return accounts;
    }
}
