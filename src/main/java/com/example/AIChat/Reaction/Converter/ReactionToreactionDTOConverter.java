package com.example.AIChat.Reaction.Converter;

import com.example.AIChat.Group.DTO.PostGroupReq;
import com.example.AIChat.Group.Domain.Group;
import com.example.AIChat.Reaction.DTO.ReactionDTO;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import com.example.AIChat.User.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ReactionToreactionDTOConverter implements Converter<ReactionBlock, ReactionDTO> {

    @Override
    public ReactionDTO convert(ReactionBlock reactionBlock) {

        ReactionDTO reactionDTO = new ReactionDTO();

        reactionDTO.setReactionId(reactionBlock.getReactionId());
        reactionDTO.setEmoji(reactionBlock.getEmoji());
        reactionDTO.setMessageId(reactionBlock.getMessageId());
        //List<String> userIds = new ArrayList<>();
        List<User> users = reactionBlock.getUser() ;

        List<String> userIds = users.stream()
                .map(User::getUserId)
                .collect(Collectors.toList());

        reactionDTO.setUserIds(userIds);



        return reactionDTO;
    }

}
