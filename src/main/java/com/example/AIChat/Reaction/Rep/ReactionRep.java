package com.example.AIChat.Reaction.Rep;

import com.example.AIChat.Message.domain.Message;
import com.example.AIChat.Reaction.Domain.ReactionBlock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRep extends JpaRepository<ReactionBlock, Long> {

    ReactionBlock findByReactionId(String reactionId);

    //ReactionBlock findAllByMessageIdAndEmoji(String messageId, String emoji);


   // List<ReactionBlock> findByMessageId(String messageId);


}
