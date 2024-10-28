package com.example.AIChat.Message.domain;

import com.example.AIChat.User.Domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String messageId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private  String userId;

    //@OneToOne(optional = false)
    @Column(nullable = false)
    private String groupId;

    @Column(nullable = false)
    private int messageType;

    @Column(nullable = false, name = "ai_replied_id")
    private String AiRepliedid;

    @Column(nullable = false)
    private Timestamp created;

}
