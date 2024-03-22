package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message createMessage(Message message) {
        if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            throw new IllegalArgumentException("Message text must not be blank and must be less than 255 characters.");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public int deleteMessage(Integer messageId) {
        try {
            Optional<Message> message = messageRepository.findById(messageId);
            if (message.isPresent()) {
                messageRepository.deleteById(messageId);
                return 1; // If deletion successful, return 1 indicating one row affected
            } else {
                return 0; // If no message found with the given ID, return 0
            }
        } catch (Exception e) {
            throw e; // Throw exception for other errors
        }
    }


    public List<Message> getAllMessagesByAccountId(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }


    public Optional<Message> getMessageByMessageId(Integer messageId){
        return messageRepository.findById(messageId);
    }

    public int updateMessageTextByMessageId(Integer messageId, String updatedMessageText){
        int rowsAffected = 0;
        
        //Perform updatedMessageText validation
        if (updatedMessageText == null || updatedMessageText.isEmpty() || updatedMessageText.length() > 255){
            return 0; //validation fails
        }

        //Valdiation passes       
        try {
            // Update the message text in the database using the repository method
            rowsAffected = messageRepository.updateMessageText(messageId, updatedMessageText);
    
         } catch (Exception e) {
            // Handle any exceptions
          }
        return rowsAffected; //return the number of rows affected
    }
}