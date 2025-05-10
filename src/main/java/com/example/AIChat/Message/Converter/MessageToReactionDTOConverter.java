package com.example.AIChat.Message.Converter;

import com.example.AIChat.Message.DTO.MessageReaction;
import com.example.AIChat.Message.domain.MessageType;
import com.example.AIChat.Reaction.DTO.ReactionDTO;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import com.example.AIChat.User.Domain.User;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.example.AIChat.Message.domain.Message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MessageToReactionDTOConverter implements Converter<Message, MessageReaction> {

    @Override
    public MessageReaction convert(@NotNull Message message) {

        MessageReaction messageReaction = new MessageReaction();

        messageReaction.setMessageId(message.getMessageId());
        messageReaction.setMessage(message.getMessage());
        messageReaction.setMessageType(message.getMessageType());
        messageReaction.setUser(message.getUser());
        messageReaction.setCreated(message.getCreated());
        messageReaction.setGroupId(message.getGroupId());
        messageReaction.setMessageNested(message.getMessageNested());

        Set<ReactionBlock> reactionBlocks = message.getReactionBlocks();

        Set<ReactionDTO> reactionDTOs = new HashSet<>();



        for(ReactionBlock reactionBlock : reactionBlocks){

            List<String> userIds = new ArrayList<>();

            ReactionDTO reactionDTO = new ReactionDTO();

            //reactionDTO.setReactionId(reactionBlock.getReactionId());
            reactionDTO.setMessageId(reactionBlock.getMessage().getMessageId());
            reactionDTO.setEmoji(reactionBlock.getEmoji());

            Set<User> users = reactionBlock.getUser();

            for(User user : users){

                userIds.add(user.getUserId());

            }


            reactionDTO.setUserIds(userIds);

            reactionDTOs.add(reactionDTO);
        }


        messageReaction.setReactionBlocks(reactionDTOs);

        return messageReaction;
    }
}
