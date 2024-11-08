package com.example.AIChat.Message.DTO;

import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.User.Domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
public class MessageDTO {

    @NotNull(message = "messageId data is missing")
    private String messageId;

    @NotNull(message = "message data is missing")
    private String message;

    @NotNull(message = "userId data is missing")
    private String userId;

    @NotNull(message = "groupId data is missing")
    private String groupId;

    @NotNull(message = "messageType data is missing")
    private MessageType messageType;

    @NotNull(message = "AiRepliedid data is missing")
    private String AiRepliedid;

    @NotNull(message = "created data is missing")
    private Timestamp created;

}