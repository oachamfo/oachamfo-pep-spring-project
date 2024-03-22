package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.DuplicateUsernameException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Username must not be blank and password must be at least 4 characters long.");
        }

        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new DuplicateUsernameException("Username already exists.");
        }

        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if (existingAccount == null || !existingAccount.getPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        return existingAccount;
    }
}
