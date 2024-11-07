package com.example.AIChat.Message.domain;

import com.example.AIChat.User.Domain.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class MessageAnswer {

    @NotNull(message = "messageId data is missing")
    private String messageId;

    @NotNull(message = "message data is missing")
    private String message;


    private User user;

    @NotNull(message = "groupId data is missing")
    private String groupId;

    @NotNull(message = "messageType data is missing")
    private MessageType messageType;

    @NotNull(message = "AiRepliedid data is missing")
    private String AiRepliedid;

    @NotNull(message = "created data is missing")
    private Timestamp created;

    @NotNull(message = "messageAnswer data is missing")
    private Message messageAnswer;

}
