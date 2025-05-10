package com.example.AIChat.Reaction.Converter;

import com.example.AIChat.Group.Service.GroupService;
import com.example.AIChat.Message.Rep.MessageRep;
import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Reaction.DTO.NewReactionDTO;
import com.example.AIChat.Reaction.DTO.ReactionDTO;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import com.example.AIChat.User.Domain.User;
import com.example.AIChat.User.Rep.UserRep;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class NewReactionToReactionBlock implements Converter<NewReactionDTO, ReactionBlock> {

    private final UserRep userRep;
    private final MessageRep messageRep;

    private static final Logger logger = LoggerFactory.getLogger(NewReactionToReactionBlock.class);

    @Override
    public ReactionBlock convert(NewReactionDTO newReactionDTO) {

        ReactionBlock reactionBlock = new ReactionBlock();

        reactionBlock.setEmoji(newReactionDTO.getEmoji());

        logger.info(" G");

        reactionBlock.setMessage(messageRep.findByMessageId(newReactionDTO.getMessageId()));

        logger.info(" G");

        Set<User> users = new HashSet<>();
        logger.info(" G");
        users.add(userRep.findByUserId(newReactionDTO.getUserId()));
        logger.info(" G");
        reactionBlock.setUser(users);

        return reactionBlock;
    }

}
