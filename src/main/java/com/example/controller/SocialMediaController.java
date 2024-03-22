package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.DuplicateUsernameException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DuplicateUsernameException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
    List<Message> messages = messageService.getAllMessagesByAccountId(accountId);
    if (messages.isEmpty()) {
        return ResponseEntity.ok(Collections.emptyList()); // Return empty list if no messages found
    }
    return ResponseEntity.ok(messages);
}

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Optional<Message>> getMessageByMessageId(@PathVariable("message_id") Integer messageId){
        Optional<Message> message = messageService.getMessageByMessageId(messageId);
        if (message.isEmpty()){
            return ResponseEntity.ok().build(); // Return 200 status if a message is not found
        }
        return ResponseEntity.ok(message); // Return message and also a 200 stauts if found
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessageTextByMessageId(@PathVariable("message_id") Integer messageId, @RequestBody Message updatedMessageObject){
        //Extract value from message_text key
        String updatedMessageText = updatedMessageObject.getMessage_text();
        
        //Check if a row was affected
        if (messageService.updateMessageTextByMessageId(messageId, updatedMessageText) == 1){
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.badRequest().build(); //Bad request status 400
    }

}
