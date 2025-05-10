package com.example.AIChat.Reaction.Service;

import com.example.AIChat.AiChatApplication;
import com.example.AIChat.Reaction.Converter.ReactionToreactionDTOConverter;
import com.example.AIChat.Reaction.DTO.ReactionDTO;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import com.example.AIChat.Reaction.Rep.ReactionRep;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionToreactionDTOConverter reactionToreactionDTOConverter;

    private final ReactionRep reactionRep;

    private static final Logger logger = LoggerFactory.getLogger(AiChatApplication.class);

    public List<ReactionDTO> getAllReactions() {
        List<ReactionBlock> reactionBlocks = reactionRep.findAll();

        List<ReactionDTO> reactionDTOs = reactionBlocks.stream()
                .map(reactionToreactionDTOConverter::convert)
                .collect(Collectors.toList());

        logger.info("Получен список всех реакций");
        return reactionDTOs;
    }


    public ReactionDTO GetReactionsById(String reactionId){

        logger.info("Получение реакции по ID");

        ReactionDTO reactionDTO = reactionToreactionDTOConverter.convert(reactionRep.findByReactionId(reactionId));

        return reactionDTO;
    }

}
