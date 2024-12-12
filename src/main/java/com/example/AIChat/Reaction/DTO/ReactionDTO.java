package com.example.AIChat.Reaction.DTO;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReactionDTO {

    @Column(nullable = false)
    private String reactionId;

    @Column(nullable = false)
    private List<String> userIds;

    @Column(nullable = false)
    private String emoji;
    @Column(nullable = false)
    private String messageId;

}
