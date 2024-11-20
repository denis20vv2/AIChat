package com.example.AIChat.Message.domain;

import com.example.AIChat.User.Domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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

    @OneToOne(optional = true)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = true)
    private User user;

    @Column(nullable = false)
    private String groupId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(nullable = false)
    @Max(9999999999999L)
    private long created;

    @OneToOne(optional = true)
    @JoinColumn(name = "ai_replied_id", referencedColumnName = "messageId", nullable = true)
    private Message messageNested ;



}
