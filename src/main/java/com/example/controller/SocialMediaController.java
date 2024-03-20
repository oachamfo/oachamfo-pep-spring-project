package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Collections;

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        try {
            Account registeredAccount = accountService.registerAccount(account);
            return new ResponseEntity<>(registeredAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody Account account) {
        try {
            Account loggedInAccount = accountService.loginAccount(account);
            return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        try {
            Message createdMessage = messageService.createMessage(message);
            return new ResponseEntity<>(createdMessage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages); // Returns the list of messages as JSON
    }
    

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessage(@PathVariable("message_id") Integer messageId) {
        try {
            int rowsAffected = messageService.deleteMessage(messageId);
            if (rowsAffected > 0) {
                return new ResponseEntity<>(rowsAffected, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.OK); // No message found but status 200 as DELETE is idempotent
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByAccountId(@PathVariable("account_id") Integer accountId) {
    System.out.println("This is from the controller:" + accountId);
    List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
    if (messages.isEmpty()) {
        return ResponseEntity.ok(Collections.emptyList()); // Return empty list if no messages found
    }
    return ResponseEntity.ok(messages);
}

}
