package com.example.AIChat.Message.Rep;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Message.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRep extends JpaRepository<Message, Long> {
}
