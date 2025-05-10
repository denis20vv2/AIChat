package com.example.AIChat.Sockets.DTO;

import com.example.AIChat.Reaction.Domain.ReactionBlock;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ReactionSocketDTO {

    private List<ReactionBlock> ReactionBlocks = new ArrayList<>();

    @NotNull(message = "MessageId = null")
    private String messageId;

}
