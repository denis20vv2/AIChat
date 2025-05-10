package com.example.AIChat.Message.DTO;

import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.Reaction.DTO.ReactionDTO;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import com.example.AIChat.User.Domain.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MessageReaction {


    @NotNull(message = "messageId data is missing")
    private String messageId;

    @NotNull(message = "message data is missing")
    private String message;

    @NotNull(message = "user data is missing")
    private User user;

    @NotNull(message = "groupId data is missing")
    private String groupId;

    @NotNull(message = "messageType data is missing")
    private MessageType messageType;
    @NotNull(message = "created data is missing")
    @Max(9999999999999L)
    private long created;


    private Message messageNested ;

    private Set<ReactionDTO> reactionBlocks = new HashSet<>();

}
