package com.example.AIChat.Message.Rep;

import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.User.Domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

@Repository
public interface MessageRep extends JpaRepository<Message, Long> {

    Message findByMessageId(String messageId);


    Page<Message> findByGroupId(String groupId, Pageable pageable);


}
