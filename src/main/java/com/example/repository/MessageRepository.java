package com.example.repository;

import com.example.entity.Message;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    //Spring was not able to automatically generate the query
    //No property postedBy found for type Message! Did you mean 'posted_by'?
    @Query("SELECT m FROM Message m WHERE m.posted_by = ?1")
    List<Message> findByPostedBy(Integer accountId);

    @Modifying
    @Transactional
    // Updates the message text of a message with the provided ID
    @Query("UPDATE Message m SET m.message_text = :updatedMessageText WHERE m.message_id = :messageId")
    int updateMessageText(@PathParam("messageId") Integer messageId, @PathParam("updatedMessageText") String updatedMessageText);

}
