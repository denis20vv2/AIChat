package com.example.AIChat.Reaction.DTO;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewReactionDTO {

    @Column(nullable = false)
    private String userId;

    @Column(nullable = true)
    private String emoji;

    @Column(nullable = false)
    private String messageId;

}
