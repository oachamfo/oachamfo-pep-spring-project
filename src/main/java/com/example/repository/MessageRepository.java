package com.example.repository;

import com.example.entity.Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    //Spring was not able to automatically generate the query
    //No property postedBy found for type Message! Did you mean 'posted_by'?
    @Query("SELECT m FROM Message m WHERE m.posted_by = ?1")
    List<Message> findByPostedBy(Integer accountId);
}
