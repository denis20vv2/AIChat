package com.example.AIChat.Sockets.DTO;

import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.User.Domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class RequestForAI {

    @NotNull(message = "messageId is null")
    private String messageId;

    @NotNull(message = "message is null")
    private String message;

    @NotNull(message = "user is null")
    private User user;

    @NotNull(message = "groupId is null")
    private String groupId;

    @NotNull(message = "messageType is null")
    private MessageType messageType;

    @NotNull(message = "AiRepliedid is null")
    private String AiRepliedid;

    @NotNull(message = "created is null")
    private int created;

}
