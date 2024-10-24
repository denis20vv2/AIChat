package com.example.AIChat.Message.domain;

import com.example.AIChat.User.Domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {

    @Id
    @Column(nullable = false)
    private String messageId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private  String userId;

    @Column(nullable = false)
    private String groupId;

    @Column(nullable = false)
    private MessageType messageType;

    @Column(nullable = false)
    private String AiRepliedid;

    @Column(nullable = false)
    private Timestamp created;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private User user;

}
